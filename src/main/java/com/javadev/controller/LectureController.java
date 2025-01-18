package com.javadev.controller;

import com.javadev.dto.CreateLectureRequest;
import com.javadev.dto.LectureDto;
import com.javadev.dto.LectureResponse;
import com.javadev.dto.RegisterLecture;
import com.javadev.service.LectureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class LectureController {

  private final LectureService lectureService;


  public LectureController(LectureService lectureService) {
    this.lectureService = lectureService;
  }

  @PostMapping
  public ResponseEntity<LectureDto> createLecture(@RequestBody CreateLectureRequest lectureRequest) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.lectureService.createLecture(lectureRequest));
  }

  @PostMapping("/register/{id}")
  public ResponseEntity<LectureResponse> registerLecture(@RequestBody RegisterLecture lecture, @PathVariable("id") int lectureId) {
    return ResponseEntity.ok(this.lectureService.registerByUserId(lecture, lectureId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<LectureDto>> getLectureById(@PathVariable int id) {
    return (this.lectureService.getLectureById(id).isEmpty() || !this.lectureService.getLectureById(id).isPresent())
        ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        : ResponseEntity.status(HttpStatus.OK).body(this.lectureService.getLectureById(id));
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteLectureById(@PathVariable int id) {
    if  (this.lectureService.getLectureById(id).isEmpty() || !this.lectureService.getLectureById(id).isPresent()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    this.lectureService.deleteLectureById(id);
    return ResponseEntity.status(HttpStatus.OK).body("Lecture deleted successfully !");
  }

}
