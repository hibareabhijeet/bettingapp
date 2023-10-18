package com.assignment.dice.bettingapp.service;

import com.assignment.dice.bettingapp.common.SystemException;
import com.assignment.dice.bettingapp.model.Topup;
import com.assignment.dice.bettingapp.model.UserEntity;
import com.assignment.dice.bettingapp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired UserRepository userRepository;

  public UserEntity addUser(UserEntity entity) throws SystemException {
    if (entity.getBalance() < 10) { // All users have balance of 10 EUR upon registration.
      entity.setBalance(10);
    }
    UserEntity response = null;
    try {
      response = userRepository.save(entity);
    } catch (DataIntegrityViolationException ex) {
      throw new SystemException("Username must be unique");
    }
    return response;
  }

  public void removeUser(String username) throws SystemException {
    UserEntity user = getUserByUsername(username);
    if (user == null) {
      throw new SystemException("User does not exist");
    }
    userRepository.deleteById(user.getId());
  }

  public UserEntity getUserByUsername(String username) throws SystemException {
    return userRepository.findByUsername(username).orElse(null);
  }

  public UserEntity updateUser(UserEntity entity) throws SystemException {
    return userRepository.save(entity);
  }

  public UserEntity topupAmount(Topup topup) throws SystemException {
    UserEntity user = getUserByUsername(topup.getUsername());;
    if (user == null) {
      throw new SystemException("User does not exist");
    }
    user.setBalance(user.getBalance()+topup.getAmount());
    updateUser(user);
    return user;
  }
}
