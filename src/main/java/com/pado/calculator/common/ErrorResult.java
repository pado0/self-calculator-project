package com.pado.calculator.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ErrorResult {
    private List<FieldError> fieldErrors;
    private List<FieldError.ConstraintViolationError> violationErrors;

    private ErrorResult(final List<FieldError> fieldErrors,
                          final List<FieldError.ConstraintViolationError> violationErrors) {
        this.fieldErrors = fieldErrors;
        this.violationErrors = violationErrors;
    }

    // (4) BindingResult에 대한 ErrorResponse 객체 생성
    public static ErrorResult of(BindingResult bindingResult) {
        return new ErrorResult(FieldError.of(bindingResult), null);
    }

    // (5) Set<ConstraintViolation<?>> 객체에 대한 ErrorResponse 객체 생성
    public static ErrorResult of(Set<ConstraintViolation<?>> violations) {
        return new ErrorResult(null, FieldError.ConstraintViolationError.of(violations));
    }

    @Getter
    public static class FieldError {
        private String field;
        private String reason;
        private Object rejectedValue;

        private FieldError(String field, Object rejectedValue, String reason) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }
        // (4) BindingResult에 대한 ErrorResponse 객체 생성


        public static List<FieldError> of(BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors =
                    bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ?
                                    "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }

        @Getter
        public static class ConstraintViolationError {
            private String propertyPath;
            private Object rejectedValue;
            private String reason;

            private ConstraintViolationError(String propertyPath, Object rejectedValue,
                                             String reason) {
                this.propertyPath = propertyPath;
                this.rejectedValue = rejectedValue;
                this.reason = reason;
            }

            public static List<ConstraintViolationError> of(
                    Set<ConstraintViolation<?>> constraintViolations) {
                return constraintViolations.stream()
                        .map(constraintViolation -> new ConstraintViolationError(
                                constraintViolation.getPropertyPath().toString(),
                                constraintViolation.getInvalidValue().toString(),
                                constraintViolation.getMessage()
                        )).collect(Collectors.toList());
            }
        }

    }

    @Getter
    public static class CommonError{
        private int status;
        private String message;

        public CommonError(int status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}
