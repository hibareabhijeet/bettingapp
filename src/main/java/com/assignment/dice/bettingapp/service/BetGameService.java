package com.assignment.dice.bettingapp.service;

import com.assignment.dice.bettingapp.common.SystemException;
import com.assignment.dice.bettingapp.model.BetGame;
import com.assignment.dice.bettingapp.model.UserEntity;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BetGameService {

  @Autowired UserService userService;

  @Value("${bet.amount.min}")
  private int amountMinLimit;

  @Value("${bet.amount.max}")
  private int amountMaxLimit;

  private UserEntity validateBet(BetGame betGame) {
    if (betGame.getAmount() < 1
        || (betGame.getAmount() < amountMinLimit || betGame.getAmount() > amountMaxLimit)) {
      throw new SystemException(
          String.format(
              "Bet amount must be in Betting range [%s : %s]", amountMinLimit, amountMaxLimit));
    }
    final UserEntity user = userService.getUserByUsername(betGame.getUsername());
    if (user == null) {
      throw new SystemException("User does not exists");
    } else if ((user.getBalance() <= 0 || (user.getBalance() - betGame.getAmount() < 0))) {
      throw new SystemException("Low Balance");
    }
    return user;
  }

  public String newBet(BetGame betGame) throws SystemException {
    UserEntity user = validateBet(betGame);
    String response = "Invalid Bet";
    int randomNum = ThreadLocalRandom.current().nextInt(1, 7);
    if (randomNum == betGame.getBetNumber()) {
      user.setBalance(user.getBalance() + betGame.getAmount());
      response = "Congo, You win the bet";
    } else {
      user.setBalance(user.getBalance() - betGame.getAmount());
      response = String.format("Winning Number is : %s, Better luck next time!", randomNum);
    }
    userService.updateUser(user);
    return response;
  }
}
