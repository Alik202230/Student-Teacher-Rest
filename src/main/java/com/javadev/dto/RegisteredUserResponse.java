package com.javadev.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisteredUserResponse {

  private String title;
  private String description;
  private String teacherName;

  public RegisteredUserResponse(String title, String description, String teacherName) {
    this.title = title;
    this.description = description;
    this.teacherName = teacherName;
  }

}
