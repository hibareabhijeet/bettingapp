package com.assignment.dice.bettingapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.assignment.dice.bettingapp.common.SystemException;
import com.assignment.dice.bettingapp.model.UserEntity;
import com.assignment.dice.bettingapp.repo.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock UserRepository userRepository;

  @InjectMocks private UserService userService;

  UserEntity request;

  @BeforeEach
  public void setup() {
    request =
        UserEntity.builder().username("abhi").first_name("Abhijit").last_name("Hibare").build();
  }

  @Test
  void addUser() {
    UserEntity user =
        UserEntity.builder()
            .id(1)
            .username("abhi")
            .first_name("Abhijit")
            .last_name("Hibare")
            .balance(10)
            .build();
    when(userRepository.save(any(UserEntity.class))).thenReturn(user);
    final UserEntity response = userService.addUser(request);
    assertNotNull(response);
    assertAll(
        "user",
        () -> assertEquals("abhi", response.getUsername()),
        () -> assertEquals("Abhijit", response.getFirst_name()),
        () -> assertEquals("Hibare", response.getLast_name()),
        () -> assertEquals(10d, response.getBalance()));
  }

  @Test
  void addUserSameUserName() {
    when(userRepository.save(any(UserEntity.class)))
        .thenThrow(new DataIntegrityViolationException("Username must be unique"));
    try {
      final UserEntity response = userService.addUser(request);
    } catch (SystemException se) {
      assertEquals("Username must be unique", se.getMessage());
    }
  }

  @Test
  void removeUser() {
    when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(request));
    userService.removeUser(request.getUsername());
    verify(userRepository).deleteById(request.getId());
  }

  @Test
  public void should_throw_exception_when_user_doesnt_exist() {
    when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));

    NullPointerException thrown =
        Assertions.assertThrows(
                NullPointerException.class,
            () -> {
              userService.removeUser(request.getUsername());
            });
    assertNull(null, thrown.getMessage());
  }

  @Test
  void updateUser() {
    UserEntity user =
        UserEntity.builder()
            .id(1)
            .username("abhi")
            .first_name("Abhijit")
            .last_name("Hibare")
            .balance(100)
            .build();
    when(userRepository.save(any(UserEntity.class))).thenReturn(user);
    final UserEntity response = userService.updateUser(request);
    assertNotNull(response);
    assertAll(
        "user",
        () -> assertEquals("abhi", response.getUsername()),
        () -> assertEquals("Abhijit", response.getFirst_name()),
        () -> assertEquals("Hibare", response.getLast_name()),
        () -> assertEquals(100d, response.getBalance()));
  }
}
