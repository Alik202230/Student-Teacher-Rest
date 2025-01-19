package com.javadev.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseResponse {

  private String title;
  private String description;
  private int studentId;

  public CourseResponse(String title, String description, int studentId) {
    this.title = title;
    this.description = description;
    this.studentId = studentId;
  }

}
