package com.javadev.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String title;
  private String description;

  @ManyToOne
  private User teacher;

  @ManyToOne
  private User student;

  @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
  private List<User> registeredUsers;
}
