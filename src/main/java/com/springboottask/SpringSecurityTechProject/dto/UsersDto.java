package com.springboottask.SpringSecurityTechProject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springboottask.SpringSecurityTechProject.general.enumeration.Roles;
import com.springboottask.SpringSecurityTechProject.general.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersDto {
    private Long id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private Status status;
    private Roles role;
    private String remember;
}
