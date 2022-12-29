package com.natal.subscriptionservice.controller;

import com.natal.subscriptionservice.controller.dto.ClientEligibilityTO;
import com.natal.subscriptionservice.controller.dto.ClientResponseTO;
import com.natal.subscriptionservice.controller.dto.ClientTO;
import com.natal.subscriptionservice.controller.validator.CpfValidator;
import com.natal.subscriptionservice.exception.DuplicatedClientException;
import com.natal.subscriptionservice.exception.NotFoundException;
import com.natal.subscriptionservice.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/{document}")
    public ResponseEntity<ClientResponseTO> get(@PathVariable("document") String document){
        try{
            if (!CpfValidator.isValid(document)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            ClientResponseTO response = subscriptionService.getClientByDocument(document);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ClientResponseTO> create(@Valid @RequestBody ClientTO clientTO){
        try{
            ClientResponseTO clientResponseTO = subscriptionService.create(clientTO);
            return new ResponseEntity<>(clientResponseTO, HttpStatus.CREATED);
        }
        catch (DuplicatedClientException e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponseTO> update(@PathVariable("id") Long id, @Valid @RequestBody ClientTO clientTO) {
        try{
            ClientResponseTO response = subscriptionService.update(id, clientTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@CPF @PathVariable("id") Long id){
        try {
            subscriptionService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{document}/eligibility")
    public ResponseEntity<ClientEligibilityTO> getEligibility(@PathVariable("document") String document){
        if (!CpfValidator.isValid(document)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(subscriptionService.getClientEligibilityByDocument(document), HttpStatus.OK);
    }

    @PutMapping("/{document}/eligibility")
    public ResponseEntity<ClientEligibilityTO> setEligibility(@CPF @PathVariable("document") String document, @RequestBody ClientEligibilityTO clientEligibilityTO){
        ClientEligibilityTO eligibilityTO = subscriptionService.setClientEligibility(document, clientEligibilityTO);
        if (eligibilityTO==null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(eligibilityTO, HttpStatus.CREATED);
    }
}
