/*
 * Copyright (c) 2024 Recurso de ejemplo creado por David Bernal.
 */

package com.dbernal.user_manager_api.controller;

import com.dbernal.user_manager_api.model.PhoneEntity;
import com.dbernal.user_manager_api.model.UserEntity;
import com.dbernal.user_manager_api.repository.UserRepository;
import com.dbernal.user_manager_api.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @BeforeEach
    void init() {
        createdUserData();
    }

    @AfterEach
    void teardown() {
        deleteUser();
    }

    @Test
    @WithMockUser
    public void whenGetUser_thenResturnOk() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("juaN.perez@email.com"))
                .andExpect(jsonPath("$[0].name").value("Florida 1"));

    }

    @Test
    @WithMockUser
    public void whenGetUserByCode_thenResturnOk() throws Exception {
        MvcResult result = mockMvc.perform(get("/user/maria.gonzales@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("maria.gonzales@email.com"))
                .andExpect(jsonPath("$.name").value("Florida 2")).andReturn();
        result.getResponse();

    }

    @Test
    @WithMockUser
    public void whenGetUsersByName_thenResturnOk() throws Exception {
        mockMvc.perform(get("/users?name=Florida"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        mockMvc.perform(get("/users?name=Atlanta"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    public void whenGetUsersByName_withNotLoginInUser_thenResturnError() throws Exception {
        mockMvc.perform(get("/users?name=Florida"))
                .andExpect(status().isForbidden());

    }

    private void createdUserData()  {

        PhoneEntity phone = new PhoneEntity("12345678", "NY", "USA");
        List<PhoneEntity> phones = new ArrayList<>();
        phones.add(phone);

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setName("Florida 1");
        userEntity1.setEmail("juaN.perez@email.com");
        userEntity1.setPhones(phones);
        userRepository.save(userEntity1);

        PhoneEntity phone2 = new PhoneEntity("12345678", "NY", "USA");
        List<PhoneEntity> phones2 = new ArrayList<>();
        phones2.add(phone2);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setName("Florida 2");
        userEntity2.setEmail("maria.gonzales@email.com");
        userEntity2.setPhones(phones2);
        userRepository.save(userEntity2);

        PhoneEntity phone3 = new PhoneEntity("12345678", "NY", "USA");
        List<PhoneEntity> phones3 = new ArrayList<>();
        phones3.add(phone3);

        UserEntity userEntity3 = new UserEntity();
        userEntity3.setName("Atlanta 1");
        userEntity3.setEmail("pedro.munoz@email.com");
        userEntity3.setPhones(phones3);
        userService.saveUserAndPhones(userEntity3);

    }

    private void deleteUser() {
       userRepository.deleteAll();
    }
}
