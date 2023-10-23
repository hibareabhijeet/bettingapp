package com.assignment.dice.bettingapp.controller;

import com.assignment.dice.bettingapp.model.BetGame;
import com.assignment.dice.bettingapp.service.BetGameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

/** Handle REST API calls for betting */
@Tag(name = "Bet Controller")
@RestController
@RequestMapping("/api/game")
public class BetGameController {

  @Autowired BetGameService betGameService;

  /**
   * @param betGame Valid betGame Object with username, amount and betting Number
   * @param errors request validation errors
   * @return response with message
   */
  @Operation(summary = "Start bet")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", content = @Content),
        @ApiResponse(
            responseCode = "400",
            description = "Bet amount must be in Betting range",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "User does not exists", content = @Content)
      })
  @PostMapping("bet")
  public ResponseEntity<?> bet(@Valid @RequestBody BetGame betGame, Errors errors) {

    if (errors.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(errors.getFieldError().getDefaultMessage());
    }
    String response = null;
    try {
      response = betGameService.newBet(betGame);

    } catch (NotFoundException ne) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ne.getMessage());
    } catch (Exception ne) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ne.getMessage());
    }

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
