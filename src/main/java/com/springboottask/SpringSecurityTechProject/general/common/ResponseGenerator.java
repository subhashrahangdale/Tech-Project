package com.springboottask.SpringSecurityTechProject.general.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class ResponseGenerator {

    public ResponseEntity<CustomResponse> generateSuccessResponse(String message, HttpStatus status, Object data) {
        CustomResponse response = new CustomResponse();
        response.setMessage(message);
        response.setData(data);
        response.setStatus(status);
        response.setTimeStamp(new SimpleDateFormat("yyy.MM.dd.HH.mm.ss").format(new Date()));
        return new ResponseEntity<>(response, status);
    }

    public ResponseEntity<CustomResponse> generateErrorResponse(String message, HttpStatus status) {
        Error error = new Error();
        error.setErrorList(Stream.of(message.split(",")).collect(Collectors.toList()));
        error.setReasons(message);
        error.setStatus(HttpStatus.BAD_REQUEST.toString());
        CustomResponse response = new CustomResponse();
        response.setError(error);
        response.setTimeStamp(new SimpleDateFormat("yyy.MM.dd.HH.mm.ss").format(new Date()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}