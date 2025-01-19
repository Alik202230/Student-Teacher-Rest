package com.javadev.service.impl;

import com.javadev.dto.*;
import com.javadev.mapper.CourseMapper;
import com.javadev.model.Course;
import com.javadev.model.User;
import com.javadev.model.enums.Role;
import com.javadev.repository.CourseRepository;
import com.javadev.repository.UserRepository;
import com.javadev.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepository;
  private final UserRepository userRepository;
  private final CourseMapper courseMapper;

  public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository, CourseMapper courseMapper) {
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
    this.courseMapper = courseMapper;
  }

  @Override
  public CourseDto createCourse(CreateCourseRequest courseRequest) {
    Optional<User> teacher = this.userRepository.findById(courseRequest.getTeacherId());

    if (teacher.isEmpty() || (!teacher.get().getRole().equals(Role.TEACHER))) throw new RuntimeException("Not found");

    if (this.courseRepository.existsByTeacherId(teacher.get().getId()))
      throw new RuntimeException("Teacher with ID: " + teacher.get().getId() + " already exists");

    if (courseRequest.getTitle() == null || courseRequest.getTitle().isEmpty())
      throw new RuntimeException("Title should not be empty");

    Course lecture = Course.builder()
        .id(courseRequest.getId())
        .title(courseRequest.getTitle())
        .description(courseRequest.getDescription())
        .teacher(teacher.get())
        .build();

    Course savedLecture = this.courseRepository.save(lecture);

    return this.courseMapper.mapToCourseDto.apply(savedLecture);
  }

  @Override
  public Optional<CourseDto> getCourseById(int id) {
    return Optional.of(this.courseRepository.findById(id)
            .map(course -> this.courseMapper.mapToCourseDto.apply(course)))
        .orElseThrow(() -> new RuntimeException("Course not found"));
  }

  @Override
  public void deleteCourseById(int id) {
    this.courseRepository.findById(id)
        .ifPresentOrElse(
            (user) -> this.courseRepository.deleteById(id),
            () -> {
              throw new RuntimeException("User not found with the id of " + id);
            });
  }

  @Override
  public CourseResponse registerByUserId(RegisterCourse registerCourse, int courseId) {
    Optional<Course> optionalCourse = this.courseRepository.findById(courseId);

    if (optionalCourse.isEmpty()) {
      throw new RuntimeException("Course not found");
    }

    Course course = optionalCourse.get();
    Optional<User> optionalStudent = this.userRepository.findById(registerCourse.getStudentId());

    if (optionalStudent.isEmpty()) throw new RuntimeException("Student not found");

    User student = optionalStudent.get();
    course.setStudent(student);

    Course savedLecture = this.courseRepository.save(course);

    return this.courseMapper.mapToCourseResponse.apply(savedLecture);
  }

  @Override
  public Map<Integer, List<RegisteredCourseResponse>> getRegisteredUserByCourseId(int courseId) {
    Map<Integer, List<RegisteredCourseResponse>> studentMap = new HashMap<>();
    Optional<Course> optionalCourse = this.courseRepository.findById(courseId);

    if (optionalCourse.isEmpty()) throw new RuntimeException("Course not found");

    Course course = optionalCourse.get();
    List<User> students = this.userRepository.findByRole(Role.STUDENT);

    List<RegisteredCourseResponse> list = students.stream()
        .map(registeredCourse -> this.courseMapper.mapToRegisteredStudent.apply(registeredCourse))
        .toList();

    course.setRegisteredUsers(students);


    studentMap.put(courseId, list);
    return studentMap;
  }
}
