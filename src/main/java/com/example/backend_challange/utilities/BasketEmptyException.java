package com.example.backend_challange.utilities;

public class BasketEmptyException extends RuntimeException {
    public BasketEmptyException(String message) {
        super(message);
    }
}
