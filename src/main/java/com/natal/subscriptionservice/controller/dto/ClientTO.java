package com.natal.subscriptionservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ClientTO {
    @JsonProperty
    @NotBlank
    private String name;

    @JsonProperty
    @CPF
    private String document;

    @JsonProperty(required = false)
    private String status;

    @JsonProperty
    @Email
    private String email;

    @JsonProperty(required = true)
    private AddressTO address;

    public ClientTO() {
    }

    public ClientTO(String name, String document, String status, String email, AddressTO address) {
        this.name = name;
        this.document = document;
        this.status = status;
        this.email = email;
        this.address = address;
    }
}
