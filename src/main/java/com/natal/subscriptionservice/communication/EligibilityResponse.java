package com.natal.subscriptionservice.communication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public class EligibilityResponse {

    @JsonProperty("isEligible")
    boolean isEligible;

    @JsonProperty("reason")
    String reason;

    public boolean isEligible() {
        return isEligible;
    }

    public String getReason() {
        return reason;
    }
}
