package com.natal.subscriptionservice.controller;

import com.natal.subscriptionservice.controller.dto.ClientEligibilityTO;
import com.natal.subscriptionservice.controller.dto.ClientResponseTO;
import com.natal.subscriptionservice.controller.dto.ClientTO;
import com.natal.subscriptionservice.exception.ClientNotFoundException;
import com.natal.subscriptionservice.service.SubscriptionService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/{document}")
    public ResponseEntity<ClientResponseTO> get(@PathVariable("document") String document){
        ClientResponseTO response = subscriptionService.getClientByDocument(document);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody ClientTO clientTO){
        Long id = subscriptionService.create(clientTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponseTO> update(@PathVariable("id") Long id, @RequestBody ClientTO clientTO) throws ClientNotFoundException {
        ClientResponseTO response = subscriptionService.update(id, clientTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{document}")
    public ResponseEntity<String> delete(@PathVariable("document") String document){
        subscriptionService.delete(document);
        return new ResponseEntity<>("Done", HttpStatus.OK);
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
