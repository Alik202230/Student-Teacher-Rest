package com.javadev.dto;

import com.javadev.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateLectureRequest {

  private int id;
  private String title;
  private String description;
  private int teacherId;

  public CreateLectureRequest(int id, String title, String description, int teacherId) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.teacherId = teacherId;
  }
}
