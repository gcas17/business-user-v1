package com.nisum.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nisum.dto.CreateUserRequest;
import com.nisum.dto.LoginRequest;
import com.nisum.dto.UserDto;
import com.nisum.service.UserService;
import net.joshka.junit.json.params.JsonFileSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.json.JsonObject;

import static com.nisum.utilities.Util.parseJsonArray;
import static com.nisum.utilities.Util.parseJsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  @InjectMocks
  private UserController userController;

  @Mock
  private UserService userService;

  @ParameterizedTest
  @DisplayName("List users when successful")
  @JsonFileSource(resources = "/mock/ListUsersDto.json")
  void listUsersWhenSuccessful(JsonObject mock) throws ClassNotFoundException, JsonProcessingException {
    when(userService.listUsers()).thenReturn(Flux.fromIterable(parseJsonArray(
        mock.getJsonArray("request"), UserDto.class
    )));

    ResponseEntity<Flux<UserDto>> response = userController.listUsers().block();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(userService, times(1)).listUsers();
  }

  @ParameterizedTest
  @DisplayName("Get user by id when successful")
  @JsonFileSource(resources = "/mock/GetUserById.json")
  void getUserByIdWhenSuccessful(JsonObject mock) throws JsonProcessingException {
    UserDto userDto = parseJsonObject(mock.getJsonObject("request"), UserDto.class);
    when(userService.getUserById(1L)).thenReturn(Mono.just(userDto));

    assertEquals(userDto, userController.getUserById("1").block().getBody());
  }

  @ParameterizedTest
  @DisplayName("Create user when successful")
  @JsonFileSource(resources = "/mock/CreateUser.json")
  void createUserWhenSuccessful(JsonObject mock) throws JsonProcessingException {
    CreateUserRequest request = parseJsonObject(mock.getJsonObject("request"), CreateUserRequest.class);
    UserDto userDto = parseJsonObject(mock.getJsonObject("response"), UserDto.class);
    when(userService.createUser(request)).thenReturn(Mono.just(userDto));

    assertEquals(userDto, userController.createUser(request).block().getBody());
  }

  @ParameterizedTest
  @DisplayName("Delete user when successful")
  @JsonFileSource(resources = "/mock/DeleteUser.json")
  void deleteUserWhenSuccessful(JsonObject mock) {
    when(userService.deleteUser(1L)).thenReturn(Mono.empty());

    assertEquals(HttpStatus.OK, userController.deleteUser("1").block().getStatusCode());
  }

  @ParameterizedTest
  @DisplayName("Login user when successful")
  @JsonFileSource(resources = "/mock/LoginUser.json")
  void loginUserWhenSuccessful(JsonObject mock) throws JsonProcessingException {
    LoginRequest request = parseJsonObject(mock.getJsonObject("request"), LoginRequest.class);
    when(userService.loginUser(request.getEmail(), request.getPassword())).thenReturn(Mono.empty());

    assertEquals(HttpStatus.OK, userController.loginUser(request).block().getStatusCode());
  }

  @Test
  @DisplayName("Disable user when successful")
  void disableUserWhenSuccessful() {
    when(userService.disableUser(1L)).thenReturn(Mono.empty());

    assertEquals(HttpStatus.OK, userController.disableUser("1").block().getStatusCode());
  }

  @ParameterizedTest
  @DisplayName("Generate authorization token when successful")
  @JsonFileSource(resources = "/mock/GenerateAuthorizationToken.json")
  void generateAuthorizationTokenWhenSuccessful(JsonObject mock) {
    String email = mock.getString("request");
    String token = mock.getString("response");
    when(userService.generateAuthorizationToken(email)).thenReturn(Mono.just(token));

    assertEquals(token, userController.generateAuthorizationToken(email).block().getBody());
  }

}

