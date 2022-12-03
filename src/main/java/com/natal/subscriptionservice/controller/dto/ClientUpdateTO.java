package com.natal.subscriptionservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientUpdateTO {

    @JsonProperty
    private String name;

    @JsonProperty
    private String status;

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public ClientUpdateTO() {
    }

    public ClientUpdateTO(String name, String status) {
        this.name = name;
        this.status = status;
    }

}
