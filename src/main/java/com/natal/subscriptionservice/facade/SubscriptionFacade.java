package com.natal.subscriptionservice.facade;

import com.natal.subscriptionservice.communication.EligibilityClient;
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
                repository.save(clientEntity);
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

    private ClientTO findClient(String document) {
        ClientTO clientTO = new ClientTO();
        try{
            ClientEntity clientPersisted = repository.findByDocument(clientTO.getDocument());
            if (clientPersisted !=null){
                clientTO.setName(clientPersisted.getName());
                clientTO.setDocument(clientPersisted.getDocument());
                clientTO.setStatus(clientPersisted.getStatus());
            }
            else {
                log.warn("Cliente com documento {} não encontrado!", clientTO.getDocument());
                clientTO = null;
            }
        }catch (Exception e){
            log.error("Falha ao buscar cliente com documento: {} ", document, e);
            clientTO = null;
        }
        return clientTO;
    }
}
