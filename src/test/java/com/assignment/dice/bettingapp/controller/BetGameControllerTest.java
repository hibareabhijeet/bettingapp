package com.assignment.dice.bettingapp.controller;

import com.assignment.dice.bettingapp.model.BetGame;
import com.assignment.dice.bettingapp.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BetGameControllerTest {

    @Test
    void bet() {
        BetGame bet = new BetGame();
        bet.setUsername("abhi");
        bet.setAmount(2);
        bet.setBetNumber(4);

    }
}