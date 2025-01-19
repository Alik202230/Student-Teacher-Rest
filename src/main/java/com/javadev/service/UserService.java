package com.javadev.service;

import com.javadev.dto.RegisteredUserResponse;
import com.javadev.dto.UserDto;
import com.javadev.model.User;
import com.javadev.model.enums.Role;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

  User createUser(User user);
  List<UserDto> getAllUsers();
  Optional<UserDto> getUserById(int id);
  void deleteUserById(int id);
  User updateUserById(UserDto user, int id);
  List<UserDto> getTeachers(Role role);
  List<UserDto> getStudents(Role role);
  Map<Integer, List<RegisteredUserResponse>> getRegisteredUserByCourseId(int id);
}
