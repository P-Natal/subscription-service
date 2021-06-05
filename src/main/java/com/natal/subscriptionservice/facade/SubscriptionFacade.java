package com.natal.subscriptionservice.facade;

import com.natal.subscriptionservice.controller.dto.ClientTO;
import com.natal.subscriptionservice.entity.ClientEntity;
import com.natal.subscriptionservice.repository.ClientRepository;
import com.natal.subscriptionservice.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SubscriptionFacade implements SubscriptionService {

    @Autowired
    private ClientRepository repository;

    @Override
    public void create(ClientTO clientTO) {
        ClientEntity clientEntity = new ClientEntity(clientTO.getName(), clientTO.getDocument());
        clientEntity.setStatus("ATIVO");
        repository.save(clientEntity);
    }

    @Override
    public ClientTO getClientByDocument(String document) {
        try{
            ClientTO clientTO = new ClientTO();
            ClientEntity clientPersisted = repository.findByDocument(clientTO.getDocument());
            if (clientPersisted !=null){
                clientTO.setName(clientPersisted.getName());
                clientTO.setDocument(clientPersisted.getDocument());
                clientTO.setStatus(clientPersisted.getStatus());
            }
            else {
                log.warn("Cliente com documento [{}] não encontrado!", clientTO.getDocument());
            }
        }catch (Exception e){
            log.error("Falha ao buscar cliente com documento: {} ", clientTO.getDocument(), e);
        }
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
}
