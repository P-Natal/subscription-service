package com.natal.subscriptionservice.facade;

import com.natal.subscriptionservice.communication.CreateClientEligibilityTO;
import com.natal.subscriptionservice.communication.EligibilityClient;
import com.natal.subscriptionservice.communication.EligibilityResponse;
import com.natal.subscriptionservice.controller.dto.ClientEligibilityTO;
import com.natal.subscriptionservice.controller.dto.ClientTO;
import com.natal.subscriptionservice.infrastructure.entity.AddressEntity;
import com.natal.subscriptionservice.infrastructure.entity.ClientEntity;
import com.natal.subscriptionservice.infrastructure.repository.AddressRepository;
import com.natal.subscriptionservice.infrastructure.repository.ClientRepository;
import com.natal.subscriptionservice.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class SubscriptionFacade implements SubscriptionService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private EligibilityClient eligibilityClient;

    @Override
    public void create(ClientTO clientTO) {
        String doc = clientTO.getDocument();

//        if (eligibilityClient.getEligibility(doc).isEligible()){
            ClientTO existingClient = findClient(doc);
            if(existingClient==null){
                AddressEntity addressEntity = new AddressEntity(clientTO.getAddress().getCep(), clientTO.getAddress().getNumber(), clientTO.getAddress().getComplement());
                AddressEntity persistedAddressEntity = addressRepository.save(addressEntity);
                ClientEntity clientEntity = new ClientEntity(clientTO.getName(), doc, "ATIVO", clientTO.getEmail(), persistedAddressEntity);
                log.info("Persistindo novo cliente: {}", clientEntity.toString());
                clientRepository.save(clientEntity);
            }
            else {
                log.warn("Cliente com documento {} já existe", doc);
            }
//        }
//        else {
//            log.warn("Cliente com documento {} não está elegível para cadastro", doc);
//        }
    }

    @Override
    public ClientTO getClientByDocument(String document) {
        return findClient(document);
    }

    @Override
    public void delete(String document) {
        try{
            ClientEntity clientPersisted = clientRepository.findByDocument(document);
            if (clientPersisted !=null){
                clientRepository.delete(clientPersisted);
            }
            else {
                log.warn("Cliente com documento [{}] não encontrado!", document);
            }
        }catch (Exception e){
            log.error("Falha ao deletar cliente com documento: {} ", document, e);
        }
    }

    @Override
    public void update(Long id, ClientTO clientTO) {
        clientRepository.findById(id).ifPresent(c -> {
            c.setName(clientTO.getName());
            c.setDocument(clientTO.getDocument());
            c.setStatus(clientTO.getStatus());
            c.setEmail(clientTO.getEmail());
            c.getAddressEntity().setCep(clientTO.getAddress().getCep());
            c.getAddressEntity().setNumber(clientTO.getAddress().getNumber());
            c.getAddressEntity().setComplement(clientTO.getAddress().getComplement());
            clientRepository.save(c);
        });
    }

    @Override
    public ClientEligibilityTO getClientEligibilityByDocument(String document) {
        ClientEligibilityTO clientEligibilityTO = new ClientEligibilityTO();
        log.info("Buscando elegibilidade de cliente com documento {}", document);
        EligibilityResponse response = eligibilityClient.getEligibility(document);
        if (response == null){
            log.error("Falha na consulta de elegibilidade para cliente com document {}", document);
            return null;
        }
        log.info("Resposta da consulta de elegibilidade do cliente com documento {}: {}", document, response.toString());
        clientEligibilityTO.setEligible(response.isEligible());
        clientEligibilityTO.setReason(response.getReason());
        return clientEligibilityTO;
    }

    @Override
    public void setClientEligibility(String document, ClientEligibilityTO clientEligibilityTO) {
        CreateClientEligibilityTO createClientEligibilityTO
                = new CreateClientEligibilityTO(document, clientEligibilityTO.isEligible(), clientEligibilityTO.getReason());
        EligibilityResponse response = eligibilityClient.setEligibility(createClientEligibilityTO);
        if (response == null){
            log.error("Falha na criação de elegibilidade para cliente com document {} e body {}", document, clientEligibilityTO);
        }
        else {
            log.info("Resposta da criação de elegibilidade para cliente com documento {}: {}", document, response);
        }
    }

    private ClientTO findClient(String document) {
        ClientTO clientTO = new ClientTO();
        try{
            ClientEntity clientPersisted = clientRepository.findByDocument(document);
            if (clientPersisted !=null){
                clientTO.setName(clientPersisted.getName());
                clientTO.setDocument(clientPersisted.getDocument());
                clientTO.setStatus(clientPersisted.getStatus());
            }
            else {
                log.warn("Cliente com documento {} não encontrado!", document);
                clientTO = null;
            }
        }catch (Exception e){
            log.error("Falha ao buscar cliente com documento: {} ", document, e);
            clientTO = null;
        }
        return clientTO;
    }
}
