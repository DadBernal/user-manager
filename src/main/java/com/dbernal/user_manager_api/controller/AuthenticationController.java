package com.dbernal.user_manager_api.controller;

import com.dbernal.user_manager_api.domain.Login;
import com.dbernal.user_manager_api.domain.LoginResponse;
import com.dbernal.user_manager_api.domain.User;
import com.dbernal.user_manager_api.model.UserEntity;
import com.dbernal.user_manager_api.service.AuthenticationService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserEntity> register(@Valid @RequestBody User registerUserDto) throws BadRequestException {
        UserEntity registeredUser = authenticationService.createUser(registerUserDto);

        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody Login loginInput) {

        return ResponseEntity.ok(authenticationService.login(loginInput));
    }
}