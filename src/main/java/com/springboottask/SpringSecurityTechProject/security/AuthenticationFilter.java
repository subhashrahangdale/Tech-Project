package com.springboottask.SpringSecurityTechProject.security;

import com.springboottask.SpringSecurityTechProject.general.exception.InvalidException;
import com.springboottask.SpringSecurityTechProject.general.utils.Validator;
import com.springboottask.SpringSecurityTechProject.services.UsersServices;
import com.springboottask.SpringSecurityTechProject.utils.JwtUtil;
import com.springboottask.SpringSecurityTechProject.utils.TokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@Order(1)
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UsersServices usersServices;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenManager tokenManager;

    private final String AUTH = "Authorization";
    private final String AUTH_BASE = "TechProject ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = request.getHeader(AUTH);
        if (!Validator.isNullOrEmpty(token) && token.startsWith(AUTH_BASE)) {
            try {
                token = token.substring(AUTH_BASE.length());
                String username;
                try {
                    username = jwtUtil.extractUsername(token);
                } catch (Exception e) {
                    throw new InvalidException("User not valid");
                }
                String validToken = tokenManager.getValidToken(username);
                if (token.equals(validToken)) {
                    if (!Validator.isNullOrEmpty(username) /*&& SecurityContextHolder.getContext().getAuthentication() == null*/) {
                        UserDetails userDetails = usersServices.loadUserByUsername(username);
                        if (jwtUtil.validateToken(token, userDetails)) {
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                            filterChain.doFilter(request, response);
                        } else {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Un Authorized");
                        }
                    }
                } else {
                    throw new InvalidException("User not valid");
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Un Authorized");
            }
        }else{
            filterChain.doFilter(request, response);
        }

    }
}
