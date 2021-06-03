package com.natal.subscriptionservice.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column
    private String name;

    @Column
    private String document;

    @Column
    private String status;

    @Column
    @CreatedDate
    private Date registryDate;

    public ClientEntity() {
    }

    public ClientEntity(int id, String name, String document, String status, Date registryDate) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.status = status;
        this.registryDate = registryDate;
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
}
