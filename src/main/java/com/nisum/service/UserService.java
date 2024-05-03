package com.nisum.service;

import com.nisum.dto.CreateUserRequest;
import com.nisum.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
  Flux<UserDto> listUsers();

  Mono<UserDto> getUserById(Long id);

  Mono<UserDto> createUser(CreateUserRequest request);

  Mono<Void> deleteUser(Long id);

  Mono<Void> loginUser(String email, String password);

  Mono<Void> disableUser(Long id);

  Mono<String> generateAuthorizationToken(String email);
}
