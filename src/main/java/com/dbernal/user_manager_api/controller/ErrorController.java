package com.dbernal.user_manager_api.controller;

import com.dbernal.user_manager_api.domain.CustomError;
import org.apache.coyote.BadRequestException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ErrorController {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomError handleValidationException(MethodArgumentNotValidException e) {
        return new CustomError(HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomError handleValidationException(BadRequestException e) {
        return new CustomError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomError handleValidationException(Exception e) {
        return new CustomError(HttpStatus.BAD_REQUEST.value(), e.getMessage());

    }


}
