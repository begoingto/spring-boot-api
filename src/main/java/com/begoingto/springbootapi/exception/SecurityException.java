package com.begoingto.springbootapi.exception;

import com.begoingto.springbootapi.base.BaseError;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class SecurityException {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public BaseError<?> handleAuthenticationException(AuthenticationException e){

        return BaseError.builder()
                .status(false)
                .code(HttpStatus.UNAUTHORIZED.value())
                .message("Something when wrong...!, please check.")
                .timestamp(LocalDateTime.now())
                .errors(e.getMessage())
                .build();
    }

}
