package com.springboottask.SpringSecurityTechProject.general.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomResponse {
    Object data;
    Error error;
    String timeStamp;
    String message;
    HttpStatus status;
}
