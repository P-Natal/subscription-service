package com.natal.subscriptionservice.service;

import com.natal.subscriptionservice.communication.EligibilityResponse;
import com.natal.subscriptionservice.controller.dto.ClientEligibilityTO;
import com.natal.subscriptionservice.controller.dto.ClientResponseTO;
import com.natal.subscriptionservice.controller.dto.ClientTO;
import org.springframework.stereotype.Service;

@Service
public interface SubscriptionService {
    Long create(ClientTO clientTO);
    ClientResponseTO getClientByDocument(String document);
    void delete(String document);
    ClientResponseTO update(Long id, ClientTO clientTO);
    ClientEligibilityTO getClientEligibilityByDocument(String document);
    void setClientEligibility(String document, ClientEligibilityTO clientEligibilityTO);
}
