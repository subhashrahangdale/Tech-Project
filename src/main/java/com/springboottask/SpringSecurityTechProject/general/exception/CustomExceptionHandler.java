package com.springboottask.SpringSecurityTechProject.general.exception;


import com.springboottask.SpringSecurityTechProject.general.common.CustomResponse;
import com.springboottask.SpringSecurityTechProject.general.common.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<?> handleInvalidException(Exception e, WebRequest request) {
        Error error = new Error();
        error.setErrorList(Stream.of(e.getMessage().split(",")).collect(Collectors.toList()));
        error.setReasons(e.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.toString());
        CustomResponse response = new CustomResponse();
        response.setError(error);
        response.setTimeStamp(new SimpleDateFormat("yyy.MM.dd.HH.mm.ss").format(new Date()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExceptionClass(Exception e, WebRequest request) {
        Error error = new Error();
        error.setErrorList(Stream.of(e.getMessage().split(",")).collect(Collectors.toList()));
        error.setReasons(e.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.toString());
        CustomResponse response = new CustomResponse();
        response.setError(error);
        response.setTimeStamp(new SimpleDateFormat("yyy.MM.dd.HH.mm.ss").format(new Date()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<?> handleUnauthorizedExceptionClass(HttpClientErrorException.Unauthorized e, WebRequest request) {
        Error error = new Error();
        error.setErrorList(Stream.of(Objects.requireNonNull(e.getMessage()).split(",")).collect(Collectors.toList()));
        error.setReasons(e.getMessage());
        error.setStatus(HttpStatus.UNAUTHORIZED.toString());
        CustomResponse response = new CustomResponse();
        response.setError(error);
        response.setTimeStamp(new SimpleDateFormat("yyy.MM.dd.HH.mm.ss").format(new Date()));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}
