package com.javadev.repository;

import com.javadev.model.User;
import com.javadev.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
  List<User> findByRole(Role role);
  
}
