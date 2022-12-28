package com.natal.subscriptionservice.service;

import com.natal.subscriptionservice.controller.dto.ClientEligibilityTO;
import com.natal.subscriptionservice.controller.dto.ClientResponseTO;
import com.natal.subscriptionservice.controller.dto.ClientTO;
import com.natal.subscriptionservice.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface SubscriptionService {
    ClientResponseTO create(ClientTO clientTO);
    ClientResponseTO getClientByDocument(String document) throws NotFoundException;
    void delete(Long id);
    ClientResponseTO update(Long id, ClientTO clientTO) throws NotFoundException;
    ClientEligibilityTO getClientEligibilityByDocument(String document);
    void setClientEligibility(String document, ClientEligibilityTO clientEligibilityTO);
}
