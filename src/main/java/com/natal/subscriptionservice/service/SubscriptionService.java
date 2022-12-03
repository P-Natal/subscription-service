package com.natal.subscriptionservice.service;

import com.natal.subscriptionservice.communication.EligibilityResponse;
import com.natal.subscriptionservice.controller.dto.ClientEligibilityTO;
import com.natal.subscriptionservice.controller.dto.ClientTO;
import org.springframework.stereotype.Service;

@Service
public interface SubscriptionService {
    public void create(ClientTO clientTO);
    public ClientTO getClientByDocument(String document);
    public void delete(String document);
    public void update(Long id, ClientTO clientTO);
    ClientEligibilityTO getClientEligibilityByDocument(String document);
    void setClientEligibility(String document, ClientEligibilityTO clientEligibilityTO);
}
