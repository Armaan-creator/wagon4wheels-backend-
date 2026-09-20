package com.wagon4wheels.backend.controller;

import com.wagon4wheels.backend.dto.LoginRequest;
import com.wagon4wheels.backend.dto.LoginResponse;
import com.wagon4wheels.backend.dto.SetupRequest;
import com.wagon4wheels.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/setup")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> setup(@Valid @RequestBody SetupRequest request) {
        authService.setupAdmin(request);
        return Map.of("message", "Admin user created - you can now log in.");
    }
}
