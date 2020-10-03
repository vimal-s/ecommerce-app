package com.example.demo.security;

import static com.example.demo.security.Constant.HEADER_STRING;
import static com.example.demo.security.Constant.SECRET;
import static com.example.demo.security.Constant.TOKEN_PREFIX;
import static com.example.demo.security.Constant.VALIDITY_DURATION;
import static java.lang.System.currentTimeMillis;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.model.requests.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(
                            HttpServletRequest request,
                            HttpServletResponse response) throws AuthenticationException {
        try {
            CreateUserRequest user =
                    new ObjectMapper().readValue(request.getInputStream(), CreateUserRequest.class);

            if (user != null) {
                logger.info("Attempting authentication");

                return authenticationManager
                        .authenticate(
                                new UsernamePasswordAuthenticationToken(
                                        user.getUsername(), user.getPassword(), new ArrayList<>()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Authentication failed");
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    FilterChain chain,
                    Authentication authResult) {
        logger.info("Authentication successful");

        String token =
                JWT.create()
                        .withSubject(authResult.getName())
                        .withExpiresAt(new Date(currentTimeMillis() + VALIDITY_DURATION))
                        .sign(Algorithm.HMAC512(SECRET.getBytes()));
        logger.info("Generated JWT token: " + token);

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
