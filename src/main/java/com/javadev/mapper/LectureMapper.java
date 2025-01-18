package com.javadev.mapper;

import com.javadev.dto.LectureDto;
import com.javadev.dto.LectureResponse;
import com.javadev.model.Lecture;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class LectureMapper {

  public final Function<Lecture, LectureDto> mapToLectureDto = lecture -> new LectureDto(
      lecture.getTitle(),
      lecture.getDescription(),
      lecture.getTeacher().getId()
  );

  public final Function<Lecture, LectureResponse> mapToDto = lecture -> new LectureResponse(
      lecture.getTitle(),
      lecture.getDescription(),
      lecture.getStudent().getId()
  );

}
