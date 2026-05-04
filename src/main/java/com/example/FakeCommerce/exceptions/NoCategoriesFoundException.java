package com.example.FakeCommerce.exceptions;

public class NoCategoriesFoundException extends RuntimeException {
    public NoCategoriesFoundException(String message) {
        super(message);
    }
}