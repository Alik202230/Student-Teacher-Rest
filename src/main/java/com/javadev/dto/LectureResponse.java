package com.javadev.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LectureResponse {

  private String title;
  private String description;
  private int studentId;

  public LectureResponse(String title, String description, int studentId) {
    this.title = title;
    this.description = description;
    this.studentId = studentId;
  }

}
