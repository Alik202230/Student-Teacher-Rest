package com.javadev.mapper;

import com.javadev.dto.CourseDto;
import com.javadev.dto.CourseResponse;
import com.javadev.dto.RegisteredCourseResponse;
import com.javadev.model.Course;
import com.javadev.model.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CourseMapper {

  public final Function<Course, CourseDto> mapToCourseDto = course -> new CourseDto(
      course.getTitle(),
      course.getDescription(),
      course.getTeacher().getId()
  );

  public final Function<Course, CourseResponse> mapToCourseResponse = course -> new CourseResponse(
      course.getTitle(),
      course.getDescription(),
      course.getStudent().getId()
  );

  public final Function<User, RegisteredCourseResponse> mapToRegisteredStudent = user -> new RegisteredCourseResponse(
      user.getId(),
      user.getName()
  );

}
