package com.javadev.mapper;

import com.javadev.dto.UserDto;
import com.javadev.model.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserMapper {

//  public UserDto mapToUserDto(User user) {
//    return new UserDto(
//        user.getName(),
//        user.getEmail(),
//        user.getRole()
//    );
//  }

  public final Function<User, UserDto> mapToUserDto = user -> new UserDto(
     user.getName(),
     user.getEmail(),
     user.getRole()
 );

  public final Function<UserDto, User> mapToUserEntity= userDto -> new User (
      userDto.getName(),
      userDto.getEmail(),
      userDto.getRole()
  );

}
