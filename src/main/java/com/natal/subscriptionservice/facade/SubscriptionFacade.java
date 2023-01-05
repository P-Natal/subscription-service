package com.natal.subscriptionservice.facade;

import com.natal.subscriptionservice.communication.CreateClientEligibilityTO;
import com.natal.subscriptionservice.communication.EligibilityClient;
import com.natal.subscriptionservice.communication.EligibilityResponse;
import com.natal.subscriptionservice.communication.SalesOrderClient;
import com.natal.subscriptionservice.controller.dto.AddressTO;
import com.natal.subscriptionservice.controller.dto.ClientEligibilityTO;
import com.natal.subscriptionservice.controller.dto.ClientResponseTO;
import com.natal.subscriptionservice.controller.dto.ClientTO;
import com.natal.subscriptionservice.exception.DuplicatedClientException;
import com.natal.subscriptionservice.exception.NotFoundException;
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

    @Autowired
    private SalesOrderClient salesOrderClient;

    @Override
    public ClientResponseTO create(ClientTO clientTO) {
        String doc = clientTO.getDocument();

//        if (eligibilityClient.getEligibility(doc).isEligible()){
            ClientTO existingClient = findClient(doc);
            if(existingClient==null){
                AddressEntity addressEntity = new AddressEntity(clientTO.getAddress().getCep(), clientTO.getAddress().getNumber(), clientTO.getAddress().getComplement());
                ClientEntity clientEntity = new ClientEntity(clientTO.getName(), doc, "ATIVO", clientTO.getEmail(), addressEntity);
                log.info("Persistindo novo cliente: {}", clientEntity);
                ClientEntity persistedClient = clientRepository.save(clientEntity);
                AddressTO addressTO = new AddressTO(persistedClient.getAddressEntity().getCep(), persistedClient.getAddressEntity().getNumber(), persistedClient.getAddressEntity().getComplement());
                return new ClientResponseTO(persistedClient.getName(), persistedClient.getDocument(), persistedClient.getStatus(), persistedClient.getEmail(), addressTO, persistedClient.getId(), persistedClient.getRegistryDate(), persistedClient.getLastUpdate());
            }
            else {
                throw new DuplicatedClientException("Cliente com documento " + clientTO.getDocument() + " ja existe");
            }
//        }
//        else {
//            log.warn("Cliente com documento {} não esta elegível para cadastro", doc);
//        }
    }

    @Override
    public ClientResponseTO getClientByDocument(String document) throws NotFoundException {
        ClientEntity entity = clientRepository.findByDocument(document);
        if (entity==null){
            throw new NotFoundException("Client with document "+document+" Not Found");
        }
        AddressTO addressTO = new AddressTO(entity.getAddressEntity().getCep(), entity.getAddressEntity().getNumber(), entity.getAddressEntity().getComplement());
        return new ClientResponseTO(entity.getName(), entity.getDocument(), entity.getStatus(), entity.getEmail(), addressTO, entity.getId(), entity.getRegistryDate(), entity.getLastUpdate());
    }

    @Override
    public void delete(Long id) {
        try{
            Optional<ClientEntity> clientPersisted = clientRepository.findById(id);
            if (clientPersisted.isPresent()){

                cancelPendingOrders(clientPersisted.get().getDocument());
                clearDocumentEligibility(clientPersisted.get().getDocument());

                clientRepository.delete(clientPersisted.get());
            }
            else {
                log.warn("Cliente com ID [{}] nao encontrado!", id);
            }
        }catch (Exception e){
            log.error("Falha ao deletar cliente com ID: {} ", id, e);
            throw e;
        }
    }

    private void clearDocumentEligibility(String document) {
        eligibilityClient.setEligibility(new CreateClientEligibilityTO(document, true, ""));
    }

    private void cancelPendingOrders(String document) {
        salesOrderClient.cancelOrdersByClient(document);
    }

    @Override
    public ClientResponseTO update(Long id, ClientTO clientTO) throws NotFoundException {
        ClientResponseTO response = null;
        Optional<ClientEntity> c = clientRepository.findById(id);
        if(c.isPresent()) {
            c.get().setName(clientTO.getName());
            c.get().setDocument(clientTO.getDocument());
            c.get().setStatus(clientTO.getStatus());
            c.get().setEmail(clientTO.getEmail());
            c.get().getAddressEntity().setCep(clientTO.getAddress().getCep());
            c.get().getAddressEntity().setNumber(clientTO.getAddress().getNumber());
            c.get().getAddressEntity().setComplement(clientTO.getAddress().getComplement());
            ClientEntity entity = clientRepository.save(c.get());
            AddressTO addressTO = new AddressTO(entity.getAddressEntity().getCep(), entity.getAddressEntity().getNumber(), entity.getAddressEntity().getComplement());
            response = new ClientResponseTO(entity.getName(), entity.getDocument(), entity.getStatus(), entity.getEmail(), addressTO, entity.getId(), entity.getRegistryDate(), entity.getLastUpdate());
        }
        else {
            throw new NotFoundException("Client with id "+id+" Not Found");
        }
        return response;
    }

    @Override
    public ClientEligibilityTO getClientEligibilityByDocument(String document) {
        ClientEligibilityTO clientEligibilityTO = new ClientEligibilityTO();

        ClientEntity clientEntity = clientRepository.findByDocument(document);
        if(clientEntity==null){
            clientEligibilityTO.setEligible(false);
            clientEligibilityTO.setReason("Cliente inexistente");
            return clientEligibilityTO;
        }

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
    public ClientEligibilityTO setClientEligibility(String document, ClientEligibilityTO clientEligibilityTO) {
        CreateClientEligibilityTO createClientEligibilityTO
                = new CreateClientEligibilityTO(document, clientEligibilityTO.isEligible(), clientEligibilityTO.getReason());
        EligibilityResponse response = eligibilityClient.setEligibility(createClientEligibilityTO);
        if (response == null){
            log.error("Falha na criação de elegibilidade para cliente com document {} e body {}", document, clientEligibilityTO);
            return null;
        }
        else {
            log.info("Resposta da criação de elegibilidade para cliente com documento {}: {}", document, response);
            return new ClientEligibilityTO(response.isEligible(), response.getReason());
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
