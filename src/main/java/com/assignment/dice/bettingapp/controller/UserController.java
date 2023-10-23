package com.assignment.dice.bettingapp.controller;

import com.assignment.dice.bettingapp.common.SystemException;
import com.assignment.dice.bettingapp.model.UserEntity;
import com.assignment.dice.bettingapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

/** Handles User CRUD operations */
@Tag(name = "User Controller")
@RestController
@RequestMapping(path = "/api/user", name = "User Controller")
public class UserController {
  Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired UserService userService;

  /**
   * @param username unique identifier for user
   * @return user details
   */
  @Operation(summary = "Get user details")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserEntity.class))
            }),
        @ApiResponse(responseCode = "404", description = "User Not found", content = @Content)
      })
  @GetMapping("/{username}")
  public ResponseEntity<?> getUser(@Valid @NotNull @NotEmpty @PathVariable String username) {

    final UserEntity response = userService.getUserByUsername(username);
    if (response == null) {
      logger.warn(String.format("User :%s Not found.", username));
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(String.format("User :%s Not found.", username));
    }
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * @param user details
   * @param errors request validation errors
   * @return response message
   */
  @Operation(summary = "Creates a new user")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(
            responseCode = "409",
            description = "Username must be unique",
            content = @Content)
      })
  @PostMapping
  public ResponseEntity<?> addUser(@Valid @RequestBody UserEntity user, Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(errors.getFieldError().getDefaultMessage());
    }
    try {
      userService.addUser(user);
    } catch (SystemException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    logger.info("User created successfully");
    return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
  }

  /**
   * @param username unique identifier for user
   * @return response message
   */
  @Operation(summary = "Removes a requested User")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "No Content"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Not Found"),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content)
      })
  @DeleteMapping("/{username}")
  public ResponseEntity<?> deleteUser(@Valid @NotNull @NotEmpty @PathVariable String username) {
    try {
      userService.removeUser(username);
    } catch (SystemException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    logger.info(String.format("User :%s removed.", username));
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(String.format("User :%s removed.", username));
  }
}
