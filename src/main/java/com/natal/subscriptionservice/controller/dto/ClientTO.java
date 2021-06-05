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

    public ClientTO() {
    }

    public ClientTO(String name, String document, String status) {
        this.name = name;
        this.document = document;
    }

}
