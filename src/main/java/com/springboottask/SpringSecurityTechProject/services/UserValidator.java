package com.springboottask.SpringSecurityTechProject.services;

import com.springboottask.SpringSecurityTechProject.dto.UsersDto;
import com.springboottask.SpringSecurityTechProject.entity.Users;
import com.springboottask.SpringSecurityTechProject.general.enumeration.Roles;
import com.springboottask.SpringSecurityTechProject.general.enumeration.Status;
import com.springboottask.SpringSecurityTechProject.general.exception.InvalidException;
import com.springboottask.SpringSecurityTechProject.utils.PasswordUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserValidator {
    private @NonNull UsersServices usersServices;

    public Users validateUser(UsersDto user) throws Exception {
        if (user.getFirstName() == null) {
            throw new InvalidException("USER FIRST NAME IS NOT AVAILABLE");
        }
        if (user.getLastName() == null) {
            throw new InvalidException("USER LAST NAME IS NOT AVAILABLE");
        }
        if (user.getUserName() == null) {
            throw new InvalidException("USER NAME IS NOT AVAILABLE");
        } else {
            Optional<Users> usersOptional = usersServices.getUserByUserName(user.getUserName().trim());
            if (usersOptional.isPresent()) {
                throw new InvalidException("USER NAME IS ALREADY AVAILABLE");
            }
        }
        if (user.getPassword() == null) {
            throw new InvalidException("PASSWORD IS NOT AVAILABLE");
        }
        user.setPassword(PasswordUtils.encryptedPassword(user.getPassword()));
        return Users.builder().username(user.getUserName()).firstName(user.getFirstName()).lastName(user.getLastName()).password(user.getPassword()).role(user.getRole() == null ? Roles.USER : user.getRole()).status(Status.ACTIVE).build();
    }

    public UsersDto buildUserToDto(Users users) {
        UsersDto userDto = UsersDto.builder().id(users.getId()).userName(users.getUsername()).firstName(users.getFirstName()).lastName(users.getLastName())
                .status(users.getStatus()).role(users.getRole()).build();
        return userDto;
    }

    public void loginUserValidate(UsersDto user) {
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            throw new InvalidException("Username not available");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new InvalidException("Password not available");
        }
    }

}
