package com.springboottask.SpringSecurityTechProject.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenManager {
    private static final Map<String, String> tokenMap = new HashMap<>();

    public void addToken(String username, String token) {
        tokenMap.put(username, token);
    }

    public String getValidToken(String username) {
        return tokenMap.getOrDefault(username, null);
    }

    public String invalidateToke(String username) {
        return tokenMap.remove(username);
    }
}
