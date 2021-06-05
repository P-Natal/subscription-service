package com.natal.subscriptionservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Client {
    private String name;
    private String document;
    private String STATUS;
}
