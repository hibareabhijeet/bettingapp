package com.assignment.dice.bettingapp.model;

import javax.validation.constraints.*;
import lombok.Data;

/** Request Object fot betting controller */
@Data
public class BetGame {
  @NotEmpty(message = "Username must not be empty")
  @NotNull(message = "username must not be null")
  private String username;

  private double amount;

  @Min(value = 1, message = "betNumber must be between 1 and 6")
  @Max(value = 6, message = "betNumber must be between 1 and 6")
  private Integer betNumber;
}
