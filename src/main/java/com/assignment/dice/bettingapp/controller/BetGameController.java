package com.assignment.dice.bettingapp.controller;

import com.assignment.dice.bettingapp.model.BetGame;
import com.assignment.dice.bettingapp.model.UserEntity;
import com.assignment.dice.bettingapp.service.BetGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("game")
public class BetGameController {

    @Autowired
    BetGameService betGameService;

    @PostMapping("bet1")
    public ResponseEntity<?> bet(@Valid @RequestBody BetGame betGame, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        }
        String response = null;
        try {
            response = betGameService.newBet(betGame);

        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RequestMapping
    public String helloWorld(){
        return "Hello game";
    }
}
