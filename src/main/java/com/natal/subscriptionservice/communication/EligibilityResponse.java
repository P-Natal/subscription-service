package com.natal.subscriptionservice.communication;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EligibilityResponse {

    @JsonProperty("isEligible")
    boolean isEligible;

    public boolean isEligible() {
        return isEligible;
    }
}
