package com.javadev.dto;

import com.javadev.model.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

  private String name;
  private String email;
  private Role role;

  public UserDto(String name, String email, Role role) {
    this.name = name;
    this.email = email;
    this.role = role;
  }

}
