package com.nisum.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nisum.dto.LoginRequest;
import com.nisum.model.User;
import com.nisum.repository.UserRepository;
import com.nisum.security.TokenProvider;
import com.nisum.utilities.Enum.ApiException;
import com.nisum.utilities.Exception.CustomApiException;
import net.joshka.junit.json.params.JsonFileSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import javax.json.JsonObject;

import static com.nisum.utilities.Util.parseJsonObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private TokenProvider tokenProvider;

  @Test
  @DisplayName("Throw CustomApiException when delete user failed")
  void throwCustomApiExceptionWhenDeleteUserFailed() {
    when(userRepository.existsById(anyLong())).thenReturn(Mono.just(false));

    CustomApiException exception = assertThrows(CustomApiException.class, () -> {
      userService.deleteUser(1L).block();
    });
    assertTrue(exception.getMessage().contains(ApiException.USR0004.getDescription()));
  }

  @ParameterizedTest
  @DisplayName("Throw CustomApiException when login user failed")
  @JsonFileSource(resources = "/mock/LoginUser.json")
  void ThrowCustomApiExceptionWhenLoginUserFailed(JsonObject mock) throws JsonProcessingException {
    LoginRequest request = parseJsonObject(mock.getJsonObject("request"), LoginRequest.class);
    User user = User.builder().email("george@gmail.com").password("123457").build();
    when(userRepository.findByEmail(request.getEmail())).thenReturn(Mono.just(user));
    CustomApiException exception = assertThrows(CustomApiException.class, () -> {
      userService.loginUser(request.getEmail(), request.getPassword()).block();
    });
    assertTrue(exception.getMessage().contains(ApiException.USR0002.getDescription()));
  }

  @ParameterizedTest
  @DisplayName("Generate authorization token when successful")
  @JsonFileSource(resources = "/mock/GenerateAuthorizationToken.json")
  void generateAuthorizationTokenWhenSuccessful(JsonObject mock) {
    String email = mock.getString("request");
    String token = mock.getString("response");
    when(userRepository.findByEmail(email)).thenReturn(Mono.just(new User()));
    when(tokenProvider.generateToken(email)).thenReturn(token);
    assertEquals(token, userService.generateAuthorizationToken(email).block());
  }
}