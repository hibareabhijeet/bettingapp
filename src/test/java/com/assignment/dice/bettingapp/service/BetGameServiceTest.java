package com.assignment.dice.bettingapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.assignment.dice.bettingapp.common.SystemException;
import com.assignment.dice.bettingapp.model.BetGame;
import com.assignment.dice.bettingapp.model.UserEntity;
import com.assignment.dice.bettingapp.repo.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class BetGameServiceTest {

  @Mock UserRepository userRepository;
  @Mock UserService userService;
  @InjectMocks private BetGameService betGameService;

  UserEntity user;
  BetGame bet;

  @BeforeEach
  public void setup() {
    user =
        UserEntity.builder()
            .username("abhi")
            .first_name("Abhijit")
            .last_name("Hibare")
            .balance(10)
            .build();
    bet = new BetGame();
    bet.setUsername("abhi");
    bet.setAmount(2);
    bet.setBetNumber(5);
    ReflectionTestUtils.setField(betGameService, "amountMinLimit", 1);
    ReflectionTestUtils.setField(betGameService, "amountMaxLimit", 12);
  }

  @Test
  void newBet() {
    bet.setAmount(-1);
    SystemException thrown =
        Assertions.assertThrows(
            SystemException.class,
            () -> {
              betGameService.newBet(bet);
            });
    assertEquals("Bet amount must be in Betting range [1 : 12]", thrown.getMessage());
  }

  @Test
  void newBetUserNotExist() {
    when(userService.getUserByUsername(user.getUsername())).thenReturn(null);
    SystemException thrown =
        Assertions.assertThrows(
            SystemException.class,
            () -> {
              betGameService.newBet(bet);
            });
    assertEquals("User does not exists", thrown.getMessage());
  }

  @Test
  void newBetLowBalance() {
    user.setBalance(0);
    when(userService.getUserByUsername(user.getUsername())).thenReturn(user);
    SystemException thrown =
        Assertions.assertThrows(
            SystemException.class,
            () -> {
              betGameService.newBet(bet);
            });
    assertEquals("Low Balance", thrown.getMessage());
  }

  @Test
  void newBetWin() {
    when(userService.getUserByUsername(user.getUsername())).thenReturn(user);
    String response = betGameService.newBet(bet);
    if (!response.contains("Better luck next time")) {
      assertEquals(response, "Congo, You win the bet");
    }
  }
}
