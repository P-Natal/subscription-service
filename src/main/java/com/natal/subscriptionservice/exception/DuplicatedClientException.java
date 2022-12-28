package com.natal.subscriptionservice.exception;

public class DuplicatedClientException extends RuntimeException {
    public DuplicatedClientException(String message) {
        super(message);
    }
}
