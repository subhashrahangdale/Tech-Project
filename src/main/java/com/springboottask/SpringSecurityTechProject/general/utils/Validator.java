package com.springboottask.SpringSecurityTechProject.general.utils;

public class Validator {

    public static Boolean isNullOrEmpty(String stringValue) {
        return stringValue == null || stringValue.isEmpty()
                || stringValue.trim().isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(isNullOrEmpty(""));
    }
}
