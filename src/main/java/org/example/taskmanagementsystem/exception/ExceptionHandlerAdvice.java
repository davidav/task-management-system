package org.example.taskmanagementsystem.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.example.taskmanagementsystem.dto.Result;
import org.example.taskmanagementsystem.dto.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleEntityNotFoundException(EntityNotFoundException ex) {
        return new Result(false, StatusCode.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleEntityNotFoundException(HttpMediaTypeNotSupportedException ex) {
        return new Result(false, StatusCode.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleEntityNotFoundException(ConstraintViolationException ex) {
        return new Result(false, StatusCode.NOT_FOUND, ex.getMessage());
    }


//    DataIntegrityViolationException
//    ConstraintDefinitionException
//    IllegalArgumentException
}
