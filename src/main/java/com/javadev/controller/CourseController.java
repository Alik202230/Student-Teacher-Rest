package com.javadev.controller;

import com.javadev.dto.*;
import com.javadev.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

  private final CourseService courseService;


  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @PostMapping
  public ResponseEntity<CourseDto> createLecture(@RequestBody CreateCourseRequest courseRequest) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.courseService.createCourse(courseRequest));
  }

  @PostMapping("/register/{id}")
  public ResponseEntity<CourseResponse> registerLecture(@RequestBody RegisterCourse course, @PathVariable("id") int courseId) {
    return ResponseEntity.ok(this.courseService.registerByUserId(course, courseId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<CourseDto>> getLectureById(@PathVariable int id) {
    return (this.courseService.getCourseById(id).isEmpty() || !this.courseService.getCourseById(id).isPresent())
        ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        : ResponseEntity.status(HttpStatus.OK).body(this.courseService.getCourseById(id));
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteLectureById(@PathVariable int id) {
    if  (this.courseService.getCourseById(id).isEmpty() || !this.courseService.getCourseById(id).isPresent()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    this.courseService.deleteCourseById(id);
    return ResponseEntity.status(HttpStatus.OK).body("Lecture deleted successfully !");
  }

  @GetMapping("/students/{id}")
  public ResponseEntity<Map<Integer, List<RegisteredCourseResponse>>> getRegisteredUser(@PathVariable("id") int courseId) {
    return ResponseEntity.ok(this.courseService.getRegisteredUserByCourseId(courseId));
  }

}
