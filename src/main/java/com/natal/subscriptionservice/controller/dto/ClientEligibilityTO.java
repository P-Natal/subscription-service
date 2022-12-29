package com.natal.subscriptionservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class ClientEligibilityTO {

    @JsonProperty("isEligible")
    @NotNull
    boolean isEligible;

    @JsonProperty("reason")
    String reason;

    public ClientEligibilityTO() {
    }

    public ClientEligibilityTO(boolean isEligible, String reason) {
        this.isEligible = isEligible;
        this.reason = reason;
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
