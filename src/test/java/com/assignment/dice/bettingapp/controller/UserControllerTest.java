package com.assignment.dice.bettingapp.controller;


import com.assignment.dice.bettingapp.model.UserEntity;
import com.assignment.dice.bettingapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getUser()  throws Exception {
        UserEntity response= UserEntity.builder().username("abhi").first_name("Abhijit").last_name("Hibare").build();
        when(userService.getUserByUsername(anyString())).thenReturn(response);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/abhi")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(),mapper.writeValueAsString(response));
    }

    @Test
    void addUser() throws Exception {
        UserEntity user= UserEntity.builder().username("abhi").first_name("Abhijit").last_name("Hibare").build();
        UserEntity response= UserEntity.builder().username("abhi").first_name("Abhijit").last_name("Hibare").build();
        when(userService.addUser(any(UserEntity.class))).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/")
                        .content(mapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    void addUserEmptyUserName() throws Exception {
        UserEntity user= UserEntity.builder().username("").first_name("Abhijit").last_name("Hibare").build();
        UserEntity response= UserEntity.builder().username("abhi").first_name("Abhijit").last_name("Hibare").build();
        when(userService.addUser(any(UserEntity.class))).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/")
                        .content(mapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("username must not be empty"));
    }

    @Test
    void deleteUser() {
    }

    @Test
    void topupAmount() {
    }
}