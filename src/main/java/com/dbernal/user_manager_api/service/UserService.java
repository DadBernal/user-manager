/*
 * Copyright (c) 2024 Recurso de ejemplo creado por David Bernal.
 */

package com.dbernal.user_manager_api.service;

import com.dbernal.user_manager_api.model.UserEntity;
import com.dbernal.user_manager_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getUsers() {
       return userRepository.findAll();
    }

    public UserEntity getUser(String email) {
        return userRepository.findByEmail(email).get();
    }

    public List<UserEntity> getUserByName(String name) {
        return userRepository.findByNameContaining(name);
    }

    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void saveUserAndPhones(UserEntity user){
        userRepository.save(user);
    }

}
