package com.pado.calculator.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class Result<T> {
    private T data;
    private String message;

    public Result(T data, String message) {
        this.data = data;
        this.message = message;
    }
}
