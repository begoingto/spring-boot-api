package com.begoingto.springbootapi.exception;

import com.begoingto.springbootapi.base.BaseError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseError<?> handleValidationException(MethodArgumentNotValidException e){

        var errors = new ArrayList<>();

        for (FieldError error : e.getFieldErrors()){
            var errorDetails = new HashMap<>();
            errorDetails.put("name",error.getField());
            errorDetails.put("message",error.getDefaultMessage());
            errors.add(errorDetails);
        }


        return BaseError.builder()
                .status(false)
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Validation is error, please check detail message!")
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();
    }
}
