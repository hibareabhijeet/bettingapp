package com.assignment.dice.bettingapp.controller;

import com.assignment.dice.bettingapp.model.Topup;
import com.assignment.dice.bettingapp.model.UserEntity;
import com.assignment.dice.bettingapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

  @GetMapping("/{username}")
  public ResponseEntity<?> getUser(@Valid @NotNull @NotEmpty @PathVariable String username) {

    final UserEntity response = userService.getUserByUsername(username);
    if (response == null) {
        logger.warn(String.format("User :%s Not found.",username));
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("User :%s Not found.",username));
    }
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping
  public ResponseEntity<?> addUser(@Valid @RequestBody UserEntity user, Errors errors) {
      if (errors.hasErrors()) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
      }
    try {
      userService.addUser(user);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
      logger.info("User created successfully");
    return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
  }

  @DeleteMapping("/{username}")
  public ResponseEntity<?> deleteUser(@Valid @NotNull @NotEmpty @PathVariable String username) {
    try {
      userService.removeUser(username);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
      logger.info(String.format("User :%s removed.",username));
    return ResponseEntity.status(HttpStatus.OK).body(String.format("User :%s removed.",username));
  }
/*
    @PutMapping("topup")
    public ResponseEntity<?> topupAmount(@Valid @RequestBody Topup topup, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        }
        final UserEntity response;
        try {
            response = userService.topupAmount(topup);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        logger.info("Top-up Amount added successfully! Total Balance="+response.getBalance());
        return ResponseEntity.status(HttpStatus.OK).body("Top-up Amount added successfully! Total Balance="+response.getBalance());
    }*/
}
