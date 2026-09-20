package com.wagon4wheels.backend.service;

import com.wagon4wheels.backend.dto.LoginRequest;
import com.wagon4wheels.backend.dto.LoginResponse;
import com.wagon4wheels.backend.dto.SetupRequest;
import com.wagon4wheels.backend.exception.SetupAlreadyCompletedException;
import com.wagon4wheels.backend.model.User;
import com.wagon4wheels.backend.repository.UserRepository;
import com.wagon4wheels.backend.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new LoginResponse(token, jwtService.getExpirationMs());
    }

    // Only works while no users exist yet - a one-time bootstrap so the
    // first admin credential is chosen by whoever runs this, never stored
    // in an env var or dashboard. Permanently closes itself after first use.
    public void setupAdmin(SetupRequest request) {
        if (userRepository.count() > 0) {
            throw new SetupAlreadyCompletedException("Setup has already been completed");
        }

        User admin = new User();
        admin.setUsername(request.getUsername());
        admin.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        admin.setRole("ADMIN");
        userRepository.save(admin);
    }
}
