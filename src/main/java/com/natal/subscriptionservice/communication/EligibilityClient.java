package com.natal.subscriptionservice.communication;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${eligibility.host}", path = "/eligibility")
public interface EligibilityClient {

    @GetMapping("/{document}")
    public EligibilityResponse getEligibility(@PathVariable("document") String document);

}
