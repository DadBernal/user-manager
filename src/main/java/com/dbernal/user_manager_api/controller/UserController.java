/*
 * Copyright (c) 2024 Recurso de ejemplo creado por David Bernal.
 */

package com.dbernal.user_manager_api.controller;

import com.dbernal.user_manager_api.model.UserEntity;
import com.dbernal.user_manager_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get the list of users in DB
     * @return List of users
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getUsers() {

        return new ResponseEntity<>(
                userService.getUsers(), HttpStatus.OK);
    }

    /**
     * Get a User by its email
     * @param email the email
     * @return A User detail
     */
    @GetMapping("/user/{email}")
    public ResponseEntity<UserEntity> getUser(@PathVariable String email) {

        return new ResponseEntity<>(
                userService.getUser(email), HttpStatus.OK);

    }

}
