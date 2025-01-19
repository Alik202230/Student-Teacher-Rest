package com.javadev.service.impl;

import com.javadev.dto.RegisteredUserResponse;
import com.javadev.dto.UserDto;
import com.javadev.mapper.UserMapper;
import com.javadev.model.Course;
import com.javadev.model.User;
import com.javadev.model.enums.Role;
import com.javadev.repository.CourseRepository;
import com.javadev.repository.UserRepository;
import com.javadev.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final CourseRepository courseRepository;

  public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, CourseRepository courseRepository) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.courseRepository = courseRepository;
  }

  @Override
  public User createUser(User user) {
    if (user.getName() == null || user.getName().isEmpty()) throw new RuntimeException("User name should not be empty");
    if (user.getEmail() == null || user.getEmail().isEmpty()) throw new RuntimeException("Email should not be empty");
    if (user.getEmail().length() < 8) throw new RuntimeException("Email should not be less then 8");
    return this.userRepository.save(user);
  }

  @Override
  public List<UserDto> getAllUsers() {
    return this.userRepository.findAll().stream()
        .map(user -> this.userMapper.mapToUserDto.apply(user))
        .toList();
  }

  @Override
  public Optional<UserDto> getUserById(int id) {
    return Optional.ofNullable(this.userRepository.findById(id)
        .map(user -> this.userMapper.mapToUserDto.apply(user))
        .orElseThrow(() -> new EntityNotFoundException("User not found with the id of " + id)));
  }

  @Override
  public void deleteUserById(int id) {
    this.userRepository.findById(id)
        .ifPresentOrElse(
            (user) -> this.userRepository.deleteById(id),
            () -> {
              throw new RuntimeException("User not found with the id of " + id);
            });
  }

  @Override
  public User updateUserById(UserDto user, int id) {
    return this.userRepository.findById(id)
        .map(currentUser -> {
          currentUser.setName(user.getName());
          currentUser.setEmail(user.getEmail());
          currentUser.setRole(user.getRole());

          return this.userRepository.save(currentUser);
        })
        .orElseThrow(() -> new EntityNotFoundException("user not found"));

  }

  @Override
  public List<UserDto> getTeachers(Role role) {
    List<User> teacher = this.userRepository.findByRole(role);
    return teacher.stream()
        .map(user -> this.userMapper.mapToUserDto.apply(user))
        .toList();
  }

  @Override
  public List<UserDto> getStudents(Role role) {
    List<User> teacher = this.userRepository.findByRole(role);
    return teacher.stream()
        .map(user -> this.userMapper.mapToUserDto.apply(user))
        .toList();
  }

  @Override
  public Map<Integer, List<RegisteredUserResponse>> getRegisteredUserByCourseId(int id) {
    Map<Integer, List<RegisteredUserResponse>> studentMap = new HashMap<>();

    Optional<User> optionalUser = this.userRepository.findById(id);

    if (optionalUser.isEmpty()) throw new RuntimeException("Student with the id " + id + " does not exist");

    User user = optionalUser.get();

    List<Course> courses = this.courseRepository.findAll();

    List<RegisteredUserResponse> listCourse = courses.stream()
        .map(course -> this.userMapper.mapToRegistered.apply(course))
        .toList();

    user.setCourses(courses);

    studentMap.put(id, listCourse);

    return studentMap;
  }
}
