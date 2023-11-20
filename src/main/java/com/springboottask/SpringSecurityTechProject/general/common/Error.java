package com.springboottask.SpringSecurityTechProject.general.common;

import lombok.Data;

import java.util.List;

@Data
public class Error {

    private List<String> errorList;
    private String status;
    private String reasons;
}
