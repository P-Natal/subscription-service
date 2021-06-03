package com.natal.subscriptionservice.service;

import com.natal.subscriptionservice.controller.dto.ClientTO;
import org.springframework.stereotype.Service;

@Service
public interface SubscriptionService {
    public void create(ClientTO clientTO);
    public void delete(ClientTO clientTO);
    public void update(ClientTO clientTO);
}
