package com.pado.calculator.api;

public class AccountCannotCreateException extends RuntimeException{

    public AccountCannotCreateException(String message) {
        super(message);
    }
}
