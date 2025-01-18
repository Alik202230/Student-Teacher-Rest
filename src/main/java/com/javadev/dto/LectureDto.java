package com.javadev.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LectureDto {

  private String title;
  private String description;
  private int studentId;

  public LectureDto(String  title, String description, int studentId) {
    this.title = title;
    this.description = description;
    this.studentId = studentId;
  }
}
