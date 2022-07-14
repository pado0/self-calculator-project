package com.pado.calculator.exception;

import com.pado.calculator.common.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    // validation 디폴트 에러 잡는용.
    @ExceptionHandler
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<ErrorResult.FieldError> errors =
                fieldErrors.stream()
                        .map(error -> new ErrorResult.FieldError(
                                error.getField(),
                                error.getDefaultMessage(),
                                error.getRejectedValue()
                        ))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(new ErrorResult(errors), HttpStatus.BAD_REQUEST);
    }
}

//    @ExceptionHandler
//    public ResponseEntity handleConstraintViolationException(
//            ConstraintViolationException e) {
//        // TODO should implement for validation
//
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    d}
