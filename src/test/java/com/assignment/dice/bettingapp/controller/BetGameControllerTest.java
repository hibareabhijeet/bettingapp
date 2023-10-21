package com.assignment.dice.bettingapp.controller;

import com.assignment.dice.bettingapp.model.BetGame;
import com.assignment.dice.bettingapp.model.UserEntity;
import com.assignment.dice.bettingapp.service.BetGameService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class BetGameControllerTest {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private BetGameService betGameService;
    @MockBean
    private UserService userService;

    @Test
    void bet() throws Exception {

        BetGame bet = new BetGame();
        bet.setUsername("abhi");
        bet.setAmount(2);
        bet.setBetNumber(5);
        UserEntity user= UserEntity.builder().username("abhi").first_name("Abhijit").last_name("Hibare").balance(10).build();
        //when(betGameService.validateBet(bet)).thenReturn(user);
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        when(betGameService.getBetWinningNumber()).thenReturn(5);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/game/bet")
                        .content(mapper.writeValueAsString(bet)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Congo, You win the bet"));
    }

    @Test
    void betUserNotFound() throws Exception {
        BetGame bet = new BetGame();
        bet.setUsername("abhi");
        bet.setAmount(2);
        bet.setBetNumber(5);

        // when(betGameService.getBetWinningNumber()).thenReturn(5);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/game/bet")
                        .content(mapper.writeValueAsString(bet)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}