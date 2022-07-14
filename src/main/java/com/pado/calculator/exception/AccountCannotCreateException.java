package com.pado.calculator.exception;

public class AccountCannotCreateException extends RuntimeException{

    public AccountCannotCreateException(String message) {
        super(message);
    }
}
