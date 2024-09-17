package com.dbernal.user_manager_api.controller;

import com.dbernal.user_manager_api.domain.Login;
import com.dbernal.user_manager_api.domain.Phone;
import com.dbernal.user_manager_api.domain.User;
import com.dbernal.user_manager_api.repository.UserRepository;
import com.dbernal.user_manager_api.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @BeforeEach
    void init() {
        createdUserData();
    }

    @AfterEach
    void teardown() {
        deleteUser();
    }

    @Test
    public void whenCreateUsers_thenReturnCreated() throws Exception {
        User newUser = new User();
        newUser.setName("Camilo Muñoz");
        newUser.setPassword("ADF1gfghf");
        newUser.setEmail("camilo1.munoz@mail.com");
        Phone phone = new Phone();
        phone.setCityCode("NY");
        phone.setNumber("12345678");
        phone.setCountryCode("USA");
        List<Phone> phones = new ArrayList<>();
        phones.add(phone);
        newUser.setPhones(phones);

        String requestJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newUser);
        mockMvc.perform(post("/auth/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Camilo Muñoz"))
                .andExpect(jsonPath("$.phones[0].cityCode").value("NY"))
                .andExpect(jsonPath("$.email").value("camilo1.munoz@mail.com"));
    }

    @Test
    public void whenCreateUsers_withSameEmail_thenReturnError() throws Exception {
        User newUser = new User();
        newUser.setName("Camilo Muñoz");
        newUser.setPassword("ADF1gfghf");
        newUser.setEmail("camilo.munoz@mail.com");
        Phone phone = new Phone();
        phone.setCityCode("NY");
        phone.setNumber("12345678");
        phone.setCountryCode("USA");
        List<Phone> phones = new ArrayList<>();
        phones.add(phone);
        newUser.setPhones(phones);

        String requestJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newUser);
        mockMvc.perform(post("/auth/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email already exists"));
    }

    @Test
    public void whenCreateUsers_withInvalidPassword_thenReturnError() throws Exception {
        User newUser = new User();
        newUser.setName("California 1");
        newUser.setPassword("ADF");
        newUser.setEmail("camilo.munoz@email.com");
        Phone phone = new Phone();
        phone.setCityCode("NY");
        phone.setNumber("12345678");
        phone.setCountryCode("USA");
        List<Phone> phones = new ArrayList<>();
        phones.add(phone);
        newUser.setPhones(phones);

        String requestJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newUser);
        mockMvc.perform(post("/auth/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Password is not valid."));
    }


    @Test
    public void whenCreateUsers_withInvalidEmail_thenReturnError() throws Exception {
        User newUser = new User();
        newUser.setName("California 1");
        newUser.setPassword("ADFdfgdf22");
        newUser.setEmail("camilo.munoz@mail");
        Phone phone = new Phone();
        phone.setCityCode("NY");
        phone.setNumber("12345678");
        phone.setCountryCode("USA");
        List<Phone> phones = new ArrayList<>();
        phones.add(phone);
        newUser.setPhones(phones);

        String requestJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newUser);
        mockMvc.perform(post("/auth/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email is not valid."));
    }

    @Test
    public void whenLogin_thenReturnOk() throws Exception {

        Login loginUser = new Login();
        loginUser.setUsername("camilo.munoz@mail.com");
        loginUser.setPassword("ADF1gfghf");

        String loginRequestJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(loginUser);
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.message").value("SUCCESS"));

    }

    @Test
    public void whenLogin_withInvalidPassword_thenReturnError() throws Exception {

        Login loginUser = new Login();
        loginUser.setUsername("camilo.munoz@mail.com");
        loginUser.setPassword("ADFhf");

        String loginRequestJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(loginUser);
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Bad credentials"));

    }

    private void createdUserData() {

        User newUser = new User();
        newUser.setName("Camilo Muñoz");
        newUser.setPassword("ADF1gfghf");
        newUser.setEmail("camilo.munoz@mail.com");
        Phone phone = new Phone();
        phone.setCityCode("NY");
        phone.setNumber("12345678");
        phone.setCountryCode("USA");
        List<Phone> phones = new ArrayList<>();
        phones.add(phone);
        newUser.setPhones(phones);
        try {
            authenticationService.createUser(newUser);
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

    }

    private void deleteUser() {
        userRepository.deleteAll();
    }

}

