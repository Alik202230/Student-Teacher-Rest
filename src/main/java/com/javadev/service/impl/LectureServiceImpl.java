package com.javadev.service.impl;

import com.javadev.dto.CreateLectureRequest;
import com.javadev.dto.LectureDto;
import com.javadev.dto.LectureResponse;
import com.javadev.dto.RegisterLecture;
import com.javadev.mapper.LectureMapper;
import com.javadev.model.Lecture;
import com.javadev.model.User;
import com.javadev.model.enums.Role;
import com.javadev.repository.LectureRepository;
import com.javadev.repository.UserRepository;
import com.javadev.service.LectureService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LectureServiceImpl implements LectureService {

  private final LectureRepository lectureRepository;
  private final UserRepository userRepository;
  private final LectureMapper lectureMapper;

  public LectureServiceImpl(LectureRepository lectureRepository, UserRepository userRepository, LectureMapper lectureMapper) {
    this.lectureRepository = lectureRepository;
    this.userRepository = userRepository;
    this.lectureMapper = lectureMapper;
  }

  @Override
  public LectureDto createLecture(CreateLectureRequest lectureRequest) {
    Optional<User> teacher = this.userRepository.findById(lectureRequest.getTeacherId());

    if (teacher.isEmpty() || (!teacher.get().getRole().equals(Role.TEACHER))) throw new RuntimeException("Not found");

    if (this.lectureRepository.existsByTeacherId(teacher.get().getId()))
      throw new RuntimeException("Teacher with ID: " + teacher.get().getId() + " already exists");

    if (lectureRequest.getTitle() == null || lectureRequest.getTitle().isEmpty())
      throw new RuntimeException("Title should not be empty");

    Lecture lecture = Lecture.builder()
        .id(lectureRequest.getId())
        .title(lectureRequest.getTitle())
        .description(lectureRequest.getDescription())
        .teacher(teacher.get())
        .build();

    Lecture savedLecture = this.lectureRepository.save(lecture);

    return this.lectureMapper.mapToLectureDto.apply(savedLecture);
  }

  @Override
  public Optional<LectureDto> getLectureById(int id) {
    return Optional.of(this.lectureRepository.findById(id)
        .map(lecture -> this.lectureMapper.mapToLectureDto.apply(lecture)))
        .orElseThrow(() -> new RuntimeException("Lecture not found"));
  }

  @Override
  public void deleteLectureById(int id) {
    this.lectureRepository.findById(id)
        .ifPresentOrElse(
            (user) -> this.lectureRepository.deleteById(id),
            () -> {
              throw new RuntimeException("User not found with the id of " + id);
            });
  }

  @Override
  public LectureResponse registerByUserId(RegisterLecture registerLecture, int lectureId) {
    Optional<Lecture> optionalLecture = this.lectureRepository.findById(lectureId);

    if (optionalLecture.isEmpty()) {
      throw new RuntimeException("Lecture not found");
    }

    Lecture lecture = optionalLecture.get();
    Optional<User> optionalStudent = this.userRepository.findById(registerLecture.getStudentId());

    if (optionalStudent.isEmpty()) throw new RuntimeException("Student not found");

    User student = optionalStudent.get();
    lecture.setStudent(student);

    Lecture savedLecture = this.lectureRepository.save(lecture);

    return this.lectureMapper.mapToDto.apply(savedLecture);
  }
}
