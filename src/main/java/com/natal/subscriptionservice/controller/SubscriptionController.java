package com.natal.subscriptionservice.controller;

import com.natal.subscriptionservice.controller.dto.ClientEligibilityTO;
import com.natal.subscriptionservice.controller.dto.ClientResponseTO;
import com.natal.subscriptionservice.controller.dto.ClientTO;
import com.natal.subscriptionservice.exception.ClientNotFoundException;
import com.natal.subscriptionservice.service.SubscriptionService;
import feign.FeignException;
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
    public ClientResponseTO get(@PathVariable("document") String document){
        return subscriptionService.getClientByDocument(document);
    }

    @PostMapping
    public Long create(@RequestBody ClientTO clientTO){
        log.info("Recebendo request de criação de cliente: {}", clientTO.toString());
        return subscriptionService.create(clientTO);
    }

    @PatchMapping("/{id}")
    public ClientResponseTO update(@PathVariable("id") Long id, @RequestBody ClientTO clientTO) throws ClientNotFoundException {
        log.info("Recebendo request de atualizacao de cliente: {}", clientTO.toString());
        return subscriptionService.update(id, clientTO);
    }

    @DeleteMapping("/{document}")
    public void delete(@PathVariable("document") String document){
        subscriptionService.delete(document);
    }

    @GetMapping("/{document}/eligibility")
    public ClientEligibilityTO getEligibility(@PathVariable("document") String document){
        return subscriptionService.getClientEligibilityByDocument(document);
    }

    @PutMapping("/{document}/eligibility")
    public void setEligibility(@PathVariable("document") String document, @RequestBody ClientEligibilityTO clientEligibilityTO){
        subscriptionService.setClientEligibility(document, clientEligibilityTO);
    }
}
