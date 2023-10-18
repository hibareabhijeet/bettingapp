package com.assignment.dice.bettingapp.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class Topup {
    @NotEmpty(message = "username must not be empty")
    @NotNull(message = "username must not be null")
    private String username;
    @Min(value=1, message = "Minimum top-up amount is 1 EURO")
    private double amount;
}
