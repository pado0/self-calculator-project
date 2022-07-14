package com.pado.calculator.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.Enumerated;
import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
public class Result<T> {

    private HttpStatus status;
    private String message;

    private T data;

    public Result(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
