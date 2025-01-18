package com.javadev.model;

import com.javadev.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String email;

  @Enumerated(EnumType.STRING)
  private Role role;

  public User(String name, String email, Role role) {
    this.name = name;
    this.email = email;
    this.role = role;
  }

}

