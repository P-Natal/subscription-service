package com.natal.subscriptionservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientEligibilityTO {

    @JsonProperty("isEligible")
    boolean isEligible;

    @JsonProperty("reason")
    String reason;

    public ClientEligibilityTO() {
    }

    @JsonProperty("isEligible")
    public boolean isEligible() {
        return isEligible;
    }

    public String getReason() {
        return reason;
    }

    @JsonProperty("isEligible")
    public void setEligible(boolean eligible) {
        isEligible = eligible;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
