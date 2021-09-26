package com.natal.subscriptionservice.communication;

import com.natal.subscriptionservice.controller.dto.ClientEligibilityTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "eligibility", url = "${eligibility.host}/eligibility")
public interface EligibilityClient {

    @GetMapping("/{document}")
    EligibilityResponse getEligibility(@PathVariable("document") String document);

    @PostMapping
    EligibilityResponse setEligibility(@RequestBody CreateClientEligibilityTO createClientEligibilityTO);
}
