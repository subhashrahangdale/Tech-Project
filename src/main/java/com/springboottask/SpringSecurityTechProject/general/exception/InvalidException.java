package com.springboottask.SpringSecurityTechProject.general.exception;

public class InvalidException extends RuntimeException {
    public InvalidException(String message) {
        super(message);
    }
    public InvalidException() {
    }
}
