package com.assignment.dice.bettingapp.model;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class BetGame {
    @NotEmpty(message = "username must not be empty")
    @NotNull(message = "username must not be null")
    private String username;
    private double amount;
    @Min(value=1, message = "betNumber must be between 1 and 6")
    @Max(value=6, message = "betNumber must be between 1 and 6")
    private Integer betNumber;
}
