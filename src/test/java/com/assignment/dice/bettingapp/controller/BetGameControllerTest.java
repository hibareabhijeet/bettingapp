package com.assignment.dice.bettingapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.assignment.dice.bettingapp.common.SystemException;
import com.assignment.dice.bettingapp.model.BetGame;
import com.assignment.dice.bettingapp.model.UserEntity;
import com.assignment.dice.bettingapp.service.BetGameService;
import com.assignment.dice.bettingapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BetGameController.class)
class BetGameControllerTest {

  BetGame bet;
  ObjectMapper mapper = new ObjectMapper();

  @Autowired MockMvc mockMvc;

  @MockBean private UserService userService;
  @MockBean private BetGameService betGameService;

  @BeforeEach
  public void setup() {
    bet = new BetGame();
    bet.setUsername("abhi");
    bet.setAmount(2);
    bet.setBetNumber(5);
  }

  @Test
  void betUserNotFound() throws Exception {
    bet.setAmount(-2);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/game/bet")
                .content(mapper.writeValueAsString(bet))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void betUserFound() throws Exception {
    // when(betGameService.getBetWinningNumber()).thenReturn(5);
    UserEntity response =
        UserEntity.builder().username("abhi").first_name("Abhijit").last_name("Hibare").build();
    when(userService.getUserByUsername(anyString())).thenReturn(response);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/game/bet")
                .content(mapper.writeValueAsString(bet))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void betUserException() throws Exception {
    // when(betGameService.getBetWinningNumber()).thenReturn(5);
    UserEntity response =
        UserEntity.builder().username("abhi").first_name("Abhijit").last_name("Hibare").build();
    when(userService.getUserByUsername(anyString())).thenReturn(response);
    doThrow(new SystemException("User does not exist"))
        .when(betGameService)
        .newBet(any(BetGame.class));
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/game/bet")
                .content(mapper.writeValueAsString(bet))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("User does not exist"));
  }

  @Test
  void betUserUnserNameEmpty() throws Exception {
    bet.setUsername("");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/game/bet")
                .content(mapper.writeValueAsString(bet))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Username must not be empty"));
  }
}
