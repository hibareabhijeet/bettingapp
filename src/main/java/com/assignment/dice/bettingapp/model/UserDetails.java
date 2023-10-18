package com.assignment.dice.bettingapp.model;

import lombok.Data;

// import javax.validation.constraints.NotNull;

@Data
public class UserDetails {
  private int id;
  //    @NotNull
  private String username;
  //    @NotNull
  private String first_name;
  private String last_name;
  private double balance;
}
