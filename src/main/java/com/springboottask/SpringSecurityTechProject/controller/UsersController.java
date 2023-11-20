package com.springboottask.SpringSecurityTechProject.controller;


import com.springboottask.SpringSecurityTechProject.dto.UsersDto;
import com.springboottask.SpringSecurityTechProject.entity.Users;
import com.springboottask.SpringSecurityTechProject.general.common.ResponseGenerator;
import com.springboottask.SpringSecurityTechProject.services.UserValidator;
import com.springboottask.SpringSecurityTechProject.services.UsersServices;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UsersController {

    private @NonNull UserValidator userValidator;
    private @NonNull UsersServices usersServices;
    private @NonNull ResponseGenerator responseGenerator;

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@RequestBody UsersDto users, @RequestHeader HttpHeaders httpHeaders) throws Exception {
        try {
            Map<String, Object> map = new HashMap<>();
            Users userInfo = userValidator.validateUser(users);
            if (userInfo != null) {
                userInfo = usersServices.save(userInfo);
                map.put("id", userInfo.getId());
            }
            return responseGenerator.generateSuccessResponse("Created successfully", HttpStatus.OK, map);
        } catch (Exception e) {
            return responseGenerator.generateErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/get/{userId}", produces = "application/json")
    public ResponseEntity<?> get(@PathVariable(value = "userId") Long userId, @RequestHeader HttpHeaders httpHeaders) {
        try {
            if (userId != null) {
                Optional<Users> users = usersServices.getUsersById(userId);
                if (users.isPresent()) {
                    return responseGenerator.generateSuccessResponse("USER FETCH SUCCESSFULLY",
                            HttpStatus.OK, userValidator.buildUserToDto(users.get()));
                }
            }
            return responseGenerator.generateErrorResponse("USER NOT FOUND", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return responseGenerator.generateErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public ResponseEntity<?> getAll(@RequestHeader HttpHeaders httpHeaders) {
        try {
                List<Users> users = usersServices.getAllUser();
                List<UsersDto> usersDtos;
                if (!users.isEmpty()) {
                    usersDtos = new ArrayList<>();
                    users.forEach(o-> usersDtos.add(UsersDto.builder().id(o.getId()).userName(o.getUsername()).firstName(o.getFirstName()).lastName(o.getLastName()).status(o.getStatus()).role(o.getRole()).build()));
                    return responseGenerator.generateSuccessResponse("USER FETCH SUCCESSFULLY",
                            HttpStatus.OK,usersDtos );
                }
            return responseGenerator.generateErrorResponse("USER NOT FOUND", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return responseGenerator.generateErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
