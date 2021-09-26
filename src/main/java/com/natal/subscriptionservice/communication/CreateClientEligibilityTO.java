package com.natal.subscriptionservice.communication;

import lombok.Data;

@Data
public class CreateClientEligibilityTO {
    private String clientDocument;
    private boolean allow;
    private String reason;

    public CreateClientEligibilityTO(String clientDocument, boolean allow, String reason) {
        this.clientDocument = clientDocument;
        this.allow = allow;
        this.reason = reason;
    }
}
