package com.natal.subscriptionservice.communication;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "eligibility", url = "${eligibility.host}/eligibility")
public interface EligibilityClient {

    @GetMapping("/{document}")
    EligibilityResponse getEligibility(@PathVariable("document") String document);

}
