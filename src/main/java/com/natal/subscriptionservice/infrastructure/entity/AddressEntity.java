package com.natal.subscriptionservice.infrastructure.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "address")
public class AddressEntity extends EntityClass {

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private int number;

    @Column(nullable = true)
    private String complement;

    public AddressEntity() {
    }

    public AddressEntity(String cep, int number, String complement) {
        this.cep = cep;
        this.number = number;
        this.complement = complement;
    }

    public AddressEntity(String cep, int number) {
        this.cep = cep;
        this.number = number;
    }

    public String getCep() {
        return cep;
    }

    public int getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "cep='" + cep + '\'' +
                ", number=" + number +
                ", complement='" + complement + '\'' +
                '}';
    }
}
