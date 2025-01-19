package com.javadev.service;

import com.javadev.dto.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CourseService {

  CourseDto createCourse(CreateCourseRequest courseRequest);
  Optional<CourseDto> getCourseById(int id);
  void deleteCourseById(int id);
  CourseResponse registerByUserId(RegisterCourse course, int courseId);
  Map<Integer, List<RegisteredCourseResponse>> getRegisteredUserByCourseId(int courseId);
}
