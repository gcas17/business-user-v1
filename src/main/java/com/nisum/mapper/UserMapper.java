package com.nisum.mapper;

import com.nisum.dto.CreateUserRequest;
import com.nisum.dto.UserDto;
import com.nisum.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(target = "phones", ignore = true)
  UserDto userToUserDtoWithoutPhones(User user);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "modified", ignore = true)
  @Mapping(target = "lastlogin", ignore = true)
  User createUserRequestToUser(CreateUserRequest createUserRequest);

  UserDto userToUserDto(User user);

  User userDtoToUser(UserDto userDto);
}
