package com.javadev.repository;

import com.javadev.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {
  boolean existsByTeacherId(int teacherId);
}
