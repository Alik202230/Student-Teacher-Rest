package com.javadev.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lecture")
public class Lecture {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String title;
  private String description;

  @ManyToOne
  private User teacher;

  @ManyToOne
  private User student;
}
