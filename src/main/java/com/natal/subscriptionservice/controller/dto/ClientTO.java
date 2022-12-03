package com.natal.subscriptionservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClientTO {
    @JsonProperty
    private String name;

    @JsonProperty
    private String document;

    @JsonProperty(required = false)
    private String status;

    @JsonProperty(required = false)
    private String email;

    @JsonProperty(required = true)
    private AddressTO address;

    public ClientTO() {
    }

    public ClientTO(String name, String document, String status, AddressTO address) {
        this.name = name;
        this.document = document;
        this.status = status;
        this.address = address;
    }
}
