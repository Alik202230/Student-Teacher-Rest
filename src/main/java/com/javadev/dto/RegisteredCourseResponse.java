package com.javadev.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisteredCourseResponse {

  private int studentId;
  private String name;

  public RegisteredCourseResponse(int studentId, String name) {
    this.studentId = studentId;
    this.name = name;
  }

}
