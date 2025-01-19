package com.javadev.repository;

import com.javadev.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
  boolean existsByTeacherId(int teacherId);
}
