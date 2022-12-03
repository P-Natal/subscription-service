package com.natal.subscriptionservice.exception;

public class ClientNotFoundException extends Exception {
    public ClientNotFoundException() {
        super("Client Not Found");
    }
}
