package com.natal.subscriptionservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ClientResponseTO extends ClientTO {

    @JsonProperty
    private Long id;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date registryDate;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date lastUpdate;

    public ClientResponseTO(String name, String document, String status, AddressTO address, Long id, Date registryDate, Date lastUpdate) {
        super(name, document, status, address);
        this.id = id;
        this.registryDate = registryDate;
        this.lastUpdate = lastUpdate;
    }
}
