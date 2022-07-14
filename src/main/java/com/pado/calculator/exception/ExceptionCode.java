package com.pado.calculator.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_DUPLICATE(404, "Member Email Already exsits"),
    EXPRESSION_NOT_VALID(404, "입력한 수식이 올바르지 않습니다");

    @Getter
    private int status;

    @Getter
    private String message;
    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
