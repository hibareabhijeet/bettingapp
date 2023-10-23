package com.assignment.dice.bettingapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.assignment.dice.bettingapp.common.SystemException;
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

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
  ObjectMapper mapper = new ObjectMapper();

  @Autowired MockMvc mockMvc;

  @MockBean private UserService userService;

  @Test
  void getUser() throws Exception {
    UserEntity response =
        UserEntity.builder().username("abhi").first_name("Abhijit").last_name("Hibare").build();
    when(userService.getUserByUsername(anyString())).thenReturn(response);
    MvcResult result =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get("/api/user/abhi").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    assertEquals(result.getResponse().getContentAsString(), mapper.writeValueAsString(response));
  }

  @Test
  void getUserInvalidUser() throws Exception {
    when(userService.getUserByUsername(anyString())).thenReturn(null);
    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/api/user/abhi").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("User :abhi Not found."));
  }

  @Test
  void addUser() throws Exception {
    UserEntity user =
        UserEntity.builder().username("abhi").first_name("Abhijit").last_name("Hibare").build();
    UserEntity response =
        UserEntity.builder().username("abhi").first_name("Abhijit").last_name("Hibare").build();
    when(userService.addUser(any(UserEntity.class))).thenReturn(response);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/user/")
                .content(mapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  void addUserEmptyUserName() throws Exception {
    UserEntity user =
        UserEntity.builder().username("").first_name("Abhijit").last_name("Hibare").build();
    UserEntity response =
        UserEntity.builder().username("abhi").first_name("Abhijit").last_name("Hibare").build();
    when(userService.addUser(any(UserEntity.class))).thenReturn(response);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/user/")
                .content(mapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Username must not be empty"));
  }

  @Test
  void addUserDuplicateUserName() throws Exception {
    UserEntity user =
        UserEntity.builder().username("abhi").first_name("Abhijit").last_name("Hibare").build();
    UserEntity response =
        UserEntity.builder().username("abhi").first_name("Abhijit").last_name("Hibare").build();
    when(userService.addUser(any(UserEntity.class)))
        .thenThrow(new SystemException("Username must be unique"));
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/user/")
                .content(mapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andExpect(content().string("Username must be unique"));
  }

  @Test
  void deleteUser() throws Exception {
    UserEntity response =
        UserEntity.builder().username("abhi").first_name("Abhijit").last_name("Hibare").build();
    when(userService.getUserByUsername(anyString())).thenReturn(response);
    this.mockMvc
        .perform(MockMvcRequestBuilders.delete("/api/user/abhi"))
        .andExpect(status().isNoContent())
        .andExpect(content().string("User :abhi removed."));
  }

  @Test
  void deleteUserNegative() throws Exception {
    this.mockMvc
        .perform(MockMvcRequestBuilders.delete("/api/user/"))
        .andExpect(status().isMethodNotAllowed());
  }

  @Test
  void deleteUserNotExists() throws Exception {
    doThrow(new SystemException("User does not exist")).when(userService).removeUser(anyString());
    this.mockMvc
        .perform(MockMvcRequestBuilders.delete("/api/user/abhi123"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("User does not exist"));
  }
}
