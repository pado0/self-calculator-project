package com.pado.calculator.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResult {
    private List<FieldError> fieldErrors;

    @Getter
    @AllArgsConstructor
    public static class FieldError{
        private String field;
        private String reason;
        private Object rejectedValue;
    }
}
