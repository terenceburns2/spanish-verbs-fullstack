package com.terenceapps.spanishverbs.controller;

import com.terenceapps.spanishverbs.config.JwtTokenUtil;
import com.terenceapps.spanishverbs.exception.EmailExistsException;
import com.terenceapps.spanishverbs.model.ErrorDetails;
import com.terenceapps.spanishverbs.model.User;
import com.terenceapps.spanishverbs.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class UserController {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authManager;
    private final UserService userService;

    public UserController(JwtTokenUtil jwtTokenUtil, AuthenticationManager authManager, UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authManager = authManager;
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<Boolean> signin(@RequestBody User user) {
        if (user.getPassword().isEmpty() || user.getEmail().isEmpty()) {
            throw new BadCredentialsException("Both email and password must be provided.");
        }

        authManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getEmail(), user.getPassword()
                        )
                );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtTokenUtil.generateToken(user)
                ).build();
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody User user) {
        if (user.getPassword().isEmpty() || user.getEmail().isEmpty()) {
            throw new BadCredentialsException("Both email and password must be provided.");
        }

        userService.registerUser(user);
        return ResponseEntity.ok().build();
    }

    /**
     * Localised exception handling
     */

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({EmailExistsException.class})
    public ErrorDetails registerConflict(Exception e) {
        return new ErrorDetails(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ErrorDetails authConflict(Exception e) {
        return new ErrorDetails(e.getMessage());
    }
}