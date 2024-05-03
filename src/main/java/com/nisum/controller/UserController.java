package com.nisum.controller;

import com.nisum.dto.CreateUserRequest;
import com.nisum.dto.LoginRequest;
import com.nisum.dto.UserDto;
import com.nisum.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/party/customer-profile/v1/users")
@AllArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping
  public Mono<ResponseEntity<Flux<UserDto>>> listUsers() {
    return userService.listUsers().
        collectList()
        .map(userList -> ResponseEntity.ok().body(Flux.fromIterable(userList)))
        .defaultIfEmpty(ResponseEntity.noContent().build());
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<UserDto>> getUserById(@PathVariable String id) {
    return userService.getUserById(Long.valueOf(id))
        .map(user -> ResponseEntity.ok(user))
        .defaultIfEmpty(ResponseEntity.noContent().build());
  }

  @PostMapping
  public Mono<ResponseEntity<UserDto>> createUser(@RequestBody CreateUserRequest request) {
    return userService.createUser(request)
        .map(createdUser -> ResponseEntity.status(HttpStatus.CREATED).body(createdUser));
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteUser(@PathVariable String id) {
    return userService.deleteUser(Long.valueOf(id))
        .then(Mono.just(ResponseEntity.ok().<Void>build()))
        .defaultIfEmpty(ResponseEntity.noContent().build());
  }

  @PostMapping("/login")
  public Mono<ResponseEntity<Void>> loginUser(@RequestBody LoginRequest loginRequest) {
    return userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword())
        .then(Mono.just(ResponseEntity.ok().<Void>build()))
        .defaultIfEmpty(ResponseEntity.noContent().build());
  }

  @PostMapping("/{id}/disable")
  public Mono<ResponseEntity<Void>> disableUser(@PathVariable String id) {
    return userService.disableUser(Long.valueOf(id))
        .then(Mono.just(ResponseEntity.ok().<Void>build()))
        .defaultIfEmpty(ResponseEntity.noContent().build());
  }

  @GetMapping("/generate-authorization-token")
  public Mono<ResponseEntity<String>> generateAuthorizationToken(String email) {
    return userService.generateAuthorizationToken(email)
        .map(token -> ResponseEntity.ok().body(token))
        .defaultIfEmpty(ResponseEntity.noContent().build());
  }
}
