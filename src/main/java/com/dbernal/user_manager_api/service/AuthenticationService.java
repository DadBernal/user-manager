/*
 * Copyright (c) 2024 Recurso de ejemplo creado por David Bernal.
 */
package com.dbernal.user_manager_api.service;

import com.dbernal.user_manager_api.domain.Login;
import com.dbernal.user_manager_api.domain.LoginResponse;
import com.dbernal.user_manager_api.domain.User;
import com.dbernal.user_manager_api.model.PhoneEntity;
import com.dbernal.user_manager_api.model.UserEntity;
import com.dbernal.user_manager_api.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthenticationService(
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            UserService userService,
            JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public UserEntity createUser(User input) throws BadRequestException {
        if(userService.getUserByEmail(input.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }
        UserEntity user = new UserEntity();
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        List<PhoneEntity> phones = new ArrayList<>();
        input.getPhones().forEach(phone -> {
            phones.add(new PhoneEntity(phone.getNumber(), phone.getCityCode(), phone.getCountryCode()));
        });
        user.setPhones(phones);
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setActive(true);
        return userRepository.save(user);
    }

    public LoginResponse login(Login login) {
        UserEntity authenticatedUser = authenticate(login);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        authenticatedUser.setToken(jwtToken);
        userRepository.save(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setMessage("SUCCESS");

        return loginResponse;
    }

    public UserEntity authenticate(Login input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getUsername())
                .orElseThrow();
    }
}
