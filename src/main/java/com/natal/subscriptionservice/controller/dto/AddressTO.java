package com.natal.subscriptionservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddressTO {

    @JsonProperty
    @NotBlank
    private String cep;

    @JsonProperty
    @NotNull
    private int number;

    @JsonProperty(required = false)
    private String complement;

    public AddressTO() {
    }

    public AddressTO(String cep, int number, String complement) {
        this.cep = cep;
        this.number = number;
        this.complement = complement;
    }

    public AddressTO(String cep, int number) {
        this.cep = cep;
        this.number = number;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }
}
