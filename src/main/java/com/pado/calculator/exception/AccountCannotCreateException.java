package com.pado.calculator.exception;

import lombok.Getter;

public class AccountCannotCreateException extends RuntimeException{

    @Getter
    private ExceptionCode exceptionCode;

    public AccountCannotCreateException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
