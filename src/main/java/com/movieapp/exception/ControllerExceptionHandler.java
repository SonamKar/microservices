package com.movieapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value=MovieNotFoundException.class)
    ResponseEntity<ErrorMessage> movieNotFoundException(MovieNotFoundException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), exception.getMessage(),
                request.getDescription(true));
    return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
    }

}
