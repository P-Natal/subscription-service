package com.natal.subscriptionservice.controller;

import com.natal.subscriptionservice.communication.EligibilityResponse;
import com.natal.subscriptionservice.controller.dto.ClientEligibilityTO;
import com.natal.subscriptionservice.controller.dto.ClientTO;
import com.natal.subscriptionservice.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/{document}")
    public ClientTO get(@PathVariable("document") String document){
        return subscriptionService.getClientByDocument(document);
    }

    @PostMapping
    public void create(@RequestBody ClientTO clientTO){
        log.info("Recebendo request de criação de cliente: {}", clientTO.toString());
        subscriptionService.create(clientTO);
    }

    @DeleteMapping("/{document}")
    public void delete(@PathVariable("document") String document){
        subscriptionService.delete(document);
    }

    @GetMapping("/{document}/eligibility")
    public ClientEligibilityTO getEligibility(@PathVariable("document") String document){
        return subscriptionService.getClientEligibilityByDocument(document);
    }

    @PostMapping("/{document}/eligibility")
    public void setEligibility(@PathVariable("document") String document, @RequestBody ClientEligibilityTO clientEligibilityTO){
        subscriptionService.setClientEligibility(document, clientEligibilityTO);
    }
}
