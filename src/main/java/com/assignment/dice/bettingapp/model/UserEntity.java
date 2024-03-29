package com.assignment.dice.bettingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.*;

/** Entity class for User and used for handling request and responses */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
    name = "user_details",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "username_unique",
          columnNames = {"username"})
    })
public class UserEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
  @SequenceGenerator(name = "id_generator", sequenceName = "user_id_seq", allocationSize = 1)
  @JsonIgnore
  private Integer id;

  @Column(unique = true)
  @NotEmpty(message = "Username must not be empty")
  @NotNull(message = "username must not be null")
  private String username;

  private String first_name;
  private String last_name;
  private double balance;
}
