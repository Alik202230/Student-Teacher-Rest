package com.javadev.service;

import com.javadev.dto.CreateLectureRequest;
import com.javadev.dto.LectureDto;
import com.javadev.dto.LectureResponse;
import com.javadev.dto.RegisterLecture;

import java.util.Optional;

public interface LectureService {

  LectureDto createLecture(CreateLectureRequest lectureRequest);
  Optional<LectureDto> getLectureById(int id);
  void deleteLectureById(int id);
  LectureResponse registerByUserId(RegisterLecture lecture, int lectureId);
}
