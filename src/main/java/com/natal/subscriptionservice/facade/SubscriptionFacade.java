package com.natal.subscriptionservice.facade;

import com.natal.subscriptionservice.controller.dto.ClientTO;
import com.natal.subscriptionservice.entity.ClientEntity;
import com.natal.subscriptionservice.repository.ClientRepository;
import com.natal.subscriptionservice.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
    public void delete(ClientTO clientTO) {

    }

    @Override
    public void update(ClientTO clientTO) {

    }
}
