package com.natal.subscriptionservice.facade;

import com.natal.subscriptionservice.communication.CreateClientEligibilityTO;
import com.natal.subscriptionservice.communication.EligibilityClient;
import com.natal.subscriptionservice.communication.EligibilityResponse;
import com.natal.subscriptionservice.controller.dto.ClientEligibilityTO;
import com.natal.subscriptionservice.controller.dto.ClientTO;
import com.natal.subscriptionservice.infrastructure.entity.ClientEntity;
import com.natal.subscriptionservice.infrastructure.repository.ClientRepository;
import com.natal.subscriptionservice.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SubscriptionFacade implements SubscriptionService {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private EligibilityClient eligibilityClient;

    @Override
    public void create(ClientTO clientTO) {
        String doc = clientTO.getDocument();

        if (eligibilityClient.getEligibility(doc).isEligible()){
            ClientTO existingClient = findClient(doc);
            if(existingClient==null){
                ClientEntity clientEntity = new ClientEntity(clientTO.getName(), doc);
                clientEntity.setStatus("ATIVO");
                log.info("Persistindo novo cliente: {}", clientEntity.toString());
                repository.save(clientEntity);
            }
            else {
                log.warn("Cliente com documento {} já existe", doc);
            }
        }
        else {
            log.warn("Cliente com documento {} não está elegível para cadastro", doc);
        }
    }

    @Override
    public ClientTO getClientByDocument(String document) {
        return findClient(document);
    }

    @Override
    public void delete(String document) {
        try{
            ClientEntity clientPersisted = repository.findByDocument(document);
            if (clientPersisted !=null){
                repository.delete(clientPersisted);
            }
            else {
                log.warn("Cliente com documento [{}] não encontrado!", document);
            }
        }catch (Exception e){
            log.error("Falha ao deletar cliente com documento: {} ", document, e);
        }
    }

    @Override
    public void update(ClientTO clientTO) {

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
            ClientEntity clientPersisted = repository.findByDocument(document);
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
