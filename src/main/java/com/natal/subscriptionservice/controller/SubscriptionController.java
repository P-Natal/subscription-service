package com.natal.subscriptionservice.controller;

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

    @PostMapping
    public void create(@RequestBody ClientTO clientTO){
        subscriptionService.create(clientTO);
    }

    @DeleteMapping
    public void delete(@PathVariable("document") String document){
        subscriptionService.delete(document);
    }

    @GetMapping
    public void get(@PathVariable("document") String document){
        subscriptionService.getClientByDocument(document);
    }
}
