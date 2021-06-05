package com.natal.subscriptionservice.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "client")
public class ClientEntity extends EntityClass {

    @Column
    private String name;

    @Column
    private String document;

    @Column
    private String status;

    public ClientEntity() {
    }

    public ClientEntity(String name, String document, String status) {
        this.name = name;
        this.document = document;
        this.status = status;
    }

    public ClientEntity(String name, String document) {
        this.name = name;
        this.document = document;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
                "id='" + this.getId() + '\'' +
                "name='" + name + '\'' +
                ", document='" + document + '\'' +
                ", status='" + status + '\'' +
                ", registry_date='" + this.getRegistryDate() + '\'' +
                '}';
    }
}
