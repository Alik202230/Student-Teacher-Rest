package com.javadev.controller;

import com.javadev.dto.RegisteredCourseResponse;
import com.javadev.dto.RegisteredUserResponse;
import com.javadev.dto.UserDto;
import com.javadev.model.User;
import com.javadev.model.enums.Role;
import com.javadev.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    return ResponseEntity.ok(this.userService.createUser(user));
  }

  @GetMapping
  public ResponseEntity<List<UserDto>> getAllUsers() {
    return ResponseEntity.ok(this.userService.getAllUsers());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUserById(@PathVariable("id") int id) {
    return (this.userService.getUserById(id).isEmpty() || !this.userService.getUserById(id).isPresent())
        ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        : ResponseEntity.status(HttpStatus.OK).body(this.userService.getUserById(id).get());
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteUserById(@PathVariable int id) {
    if (this.userService.getUserById(id).isEmpty() || !this.userService.getUserById(id).isPresent()) {
      ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    this.userService.deleteUserById(id);
    return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<User> updateUser(@RequestBody UserDto userDto, @PathVariable("id") int id) {
    return (this.userService.getUserById(id).isEmpty() || !this.userService.getUserById(id).isPresent())
        ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        : ResponseEntity.status(HttpStatus.OK).body(this.userService.updateUserById(userDto, id));
  }

  @GetMapping("/teacher")
  public ResponseEntity<List<UserDto>> getTeachers() {
    return ResponseEntity.ok(this.userService.getTeachers(Role.TEACHER));
  }

  @GetMapping("/student")
  public ResponseEntity<List<UserDto>> getStudents() {
    return ResponseEntity.ok(this.userService.getStudents(Role.STUDENT));
  }

  @GetMapping("/courses/{id}")
  public ResponseEntity<Map<Integer, List<RegisteredUserResponse>>> getRegisteredUser(@PathVariable("id") int studentId) {
    return ResponseEntity.ok(this.userService.getRegisteredUserByCourseId(studentId));
  }

}
