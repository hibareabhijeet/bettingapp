package com.assignment.dice.bettingapp.service;

import com.assignment.dice.bettingapp.common.SystemException;
import com.assignment.dice.bettingapp.model.UserEntity;
import com.assignment.dice.bettingapp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/** User service layer */
@Service
public class UserService {

  @Autowired UserRepository userRepository;

  /**
   * @param entity add user
   * @return user object
   * @throws SystemException error
   */
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

  /**
   * @param username unique identifier
   * @throws SystemException error
   */
  public void removeUser(String username) throws SystemException {
    UserEntity user = getUserByUsername(username);
    userRepository.deleteById(user.getId());
  }

  /**
   * @param username unique identifier
   * @return user object
   * @throws SystemException error
   */
  public UserEntity getUserByUsername(String username) throws SystemException {
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new SystemException("User does not exist"));
  }

  /**
   * @param entity update user
   * @return user object
   * @throws SystemException error
   */
  public UserEntity updateUser(UserEntity entity) throws SystemException {
    return userRepository.save(entity);
  }
}
