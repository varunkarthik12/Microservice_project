package com.example.employee_services.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundException(ResourceNotFoundException rnf, WebRequest wr) {
        ErrorDetails err = new ErrorDetails(
                LocalDateTime.now(),
                rnf.getMessage(),
                wr.getDescription(false),
                "User_Not_Found"
        );

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> emailAlredyExistsException(EmailAlreadyExistsException excep, WebRequest wr)
    {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                excep.getMessage(),
                wr.getDescription(false),
                "EMAIL_ALREADY_EXISTS"
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);


    }
}
