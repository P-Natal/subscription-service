package com.natal.subscriptionservice.infrastructure.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "client")
public class ClientEntity extends EntityClass {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String document;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressEntity addressEntity;

    public ClientEntity() {
    }

    public ClientEntity(String name, String document, String status, String email, AddressEntity addressEntity) {
        this.name = name;
        this.document = document;
        this.status = status;
        this.email = email;
        this.addressEntity = addressEntity;
    }

    public ClientEntity(String name, String document, String email, AddressEntity addressEntity) {
        this.name = name;
        this.document = document;
        this.email = email;
        this.addressEntity = addressEntity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    public void setAddressEntity(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
                "id='" + this.getId() + '\'' +
                "name='" + name + '\'' +
                ", document='" + document + '\'' +
                ", status='" + status + '\'' +
                ", registry_date='" + this.getRegistryDate() + '\'' +
                ", last_update='" + this.getLastUpdate() + '\'' +
                '}';
    }
}
