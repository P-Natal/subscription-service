package com.natal.subscriptionservice.controller.dto;

import lombok.Data;

@Data
public class ClientTO {
    private String name;
    private String document;
    private String status;

    public ClientTO() {
    }

    public ClientTO(String name, String document, String status) {
        this.name = name;
        this.document = document;
    }

}
