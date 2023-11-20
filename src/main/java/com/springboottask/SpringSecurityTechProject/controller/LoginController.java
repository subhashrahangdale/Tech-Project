package com.springboottask.SpringSecurityTechProject.controller;


import com.springboottask.SpringSecurityTechProject.dto.UsersDto;
import com.springboottask.SpringSecurityTechProject.general.common.ResponseGenerator;
import com.springboottask.SpringSecurityTechProject.general.exception.InvalidException;
import com.springboottask.SpringSecurityTechProject.services.UserValidator;
import com.springboottask.SpringSecurityTechProject.services.UsersServices;
import com.springboottask.SpringSecurityTechProject.utils.JwtUtil;
import com.springboottask.SpringSecurityTechProject.utils.PasswordUtils;
import com.springboottask.SpringSecurityTechProject.utils.TokenManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LoginController {

    private @NonNull ResponseGenerator responseGenerator;
    private @NonNull UsersServices usersServices;
    private @NonNull UserValidator userValidator;
    private @NonNull JwtUtil jwtUtil;
    private @NonNull TokenManager tokenManager;

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody UsersDto usersDto, @RequestHeader HttpHeaders httpHeaders) {
        try {
            userValidator.loginUserValidate(usersDto);
            usersDto.setPassword(PasswordUtils.encryptedPassword(usersDto.getPassword()));
            UserDetails userDetails = usersServices.loadUserByUsername(usersDto.getUserName());
            if (Objects.isNull(userDetails)) {
                throw new InvalidException("Invalid username");
            }
            if (!userDetails.getPassword().equals(usersDto.getPassword())) {
                throw new InvalidException("Invalid password");
            }
//            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usersDto.getUserName(), usersDto.getPassword()));
            String username = userDetails.getUsername();
            String token = jwtUtil.generateToken(userDetails);
            String validToken = tokenManager.getValidToken(username);
            if (!Objects.isNull(validToken)) {
                jwtUtil.invalidateToken(validToken);
            }
            tokenManager.addToken(username, token);
            Map<String, String> map = new HashMap();
            map.put("Authorization", token);
            return responseGenerator.generateSuccessResponse("Login Successfully", HttpStatus.OK, map);
        } catch (Exception e) {
            e.printStackTrace();
            return responseGenerator.generateErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/logout", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> logout(@RequestBody UsersDto usersDto, @RequestHeader HttpHeaders httpHeaders) {
        try {
            if(usersDto.getUserName() == null || usersDto.getUserName().isEmpty()){
                throw new InvalidException("Invalid userName");
            }
            UserDetails userDetails = usersServices.loadUserByUsername(usersDto.getUserName());
            if (Objects.isNull(userDetails)) {
                throw new InvalidException("Invalid userName");
            }
            String username = userDetails.getUsername();
            String validToken = tokenManager.getValidToken(username);
            if (!Objects.isNull(validToken)) {
                jwtUtil.invalidateToken(validToken);
                tokenManager.invalidateToke(usersDto.getUserName());
                return responseGenerator.generateSuccessResponse("Logged out successfully", HttpStatus.OK, null);
            }
            return responseGenerator.generateErrorResponse("Invalid request details", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return responseGenerator.generateErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
