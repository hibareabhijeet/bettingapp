package com.assignment.dice.bettingapp.repo;

import com.assignment.dice.bettingapp.model.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** User Repository */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
  /**
   * Return top record based on username
   *
   * @return UserEntity record
   */
  Optional<UserEntity> findByUsername(String username);
}
