package com.task.fileworks.controller;

import com.task.fileworks.common.exception.FileReadException;
import com.task.fileworks.dto.GenericExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler({FileReadException.class, IOException.class, IllegalArgumentException.class})
    public ResponseEntity<GenericExceptionDto> fileReadException(final Exception ex) {
        GenericExceptionDto response = new GenericExceptionDto();
        response.setDescription(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
