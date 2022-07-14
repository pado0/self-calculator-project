package com.pado.calculator.exception;

import com.pado.calculator.common.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    // validation 디폴트 에러 잡는용.
//    @ExceptionHandler
//    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        final List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
//        List<ErrorResult.FieldError> errors =
//                fieldErrors.stream()
//                        .map(error -> new ErrorResult.FieldError(
//                                error.getField(),
//                                error.getDefaultMessage(),
//                                error.getRejectedValue()
//                        ))
//                        .collect(Collectors.toList());
//
//        return new ResponseEntity<>(new ErrorResult(errors), HttpStatus.BAD_REQUEST);
//    }

    // of 생성 메소드로 코드 간소화
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ErrorResult result = ErrorResult.of(e.getBindingResult());
        return result;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult handleConstraintViolationException(
            ConstraintViolationException e) {
        final ErrorResult result = ErrorResult.of(e.getConstraintViolations());

        return result;
    }

    // 중복 로그인에 대한 사용자정의 오류
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleAccountCannotCreateException(AccountCannotCreateException e){

        ErrorResult.CommonError error = new ErrorResult.CommonError(e.getExceptionCode().getStatus(), e.getExceptionCode().getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
