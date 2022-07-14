package com.pado.calculator.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_DUPLICATE(404, "Member Email Already exsits");

    @Getter
    private int status;

    @Getter
    private String message;
    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
