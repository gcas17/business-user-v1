package com.nisum.service.impl;

import com.nisum.config.ApplicationProperties;
import com.nisum.dto.CreateUserRequest;
import com.nisum.dto.UserDto;
import com.nisum.mapper.UserMapper;
import com.nisum.model.Phone;
import com.nisum.model.User;
import com.nisum.repository.PhoneRepository;
import com.nisum.repository.UserRepository;
import com.nisum.security.TokenProvider;
import com.nisum.service.UserService;
import com.nisum.utilities.Util;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static com.nisum.utilities.Enum.ApiException.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PhoneRepository phoneRepository;
  private final TokenProvider tokenProvider;
  private final ApplicationProperties applicationProperties;

  @Override
  public Flux<UserDto> listUsers() {
    return userRepository.findAll().flatMap(this::getPhonesFromUser);
  }

  @Override
  public Mono<UserDto> getUserById(Long id) {
    return userRepository.findById(id).flatMap(this::getPhonesFromUser);
  }

  @Override
  public Mono<UserDto> createUser(CreateUserRequest request) {
    if (!Util.validateEmail(request.getEmail())) {
      return Mono.error(USR0006.getException());
    }
    if (!Util.validatePassword(request.getPassword(), applicationProperties.getPasswordRegex())) {
      return Mono.error(USR0007.getException());
    }
    User user = UserMapper.INSTANCE.createUserRequestToUser(request);
    String token = tokenProvider.generateToken(request.getEmail());
    return userRepository.existsByEmail(user.getEmail())
        .defaultIfEmpty(false)
        .flatMap(exists -> exists
            ? Mono.error(USR0005.getException())
            : Mono.just(user))
        .flatMap(newUser -> {
          newUser.setToken(token);
          newUser.setIsactive(true);
          newUser.setLastlogin(LocalDateTime.now());
          newUser.setCreated(LocalDateTime.now());
          newUser.setModified(LocalDateTime.now());
          return userRepository.save(newUser);
        })
        .flatMap(savedUser -> savePhonesFromUser(request, savedUser))
        .map(UserMapper.INSTANCE::userToUserDtoWithoutPhones);
  }

  @Override
  public Mono<Void> deleteUser(Long id) {
    return userRepository.existsById(id)
        .defaultIfEmpty(false)
        .flatMap(
            exists -> {
              if (!exists) return Mono.error(USR0004.getException());
              return phoneRepository
                  .findByUserId(id)
                  .flatMap(phoneRepository::delete)
                  .then(userRepository.deleteById(id));
            }
        );
  }

  @Override
  public Mono<Void> loginUser(String email, String password) {
    return userRepository.findByEmail(email)
        .switchIfEmpty(Mono.error(USR0003.getException()))
        .filter(existingUser -> existingUser.getPassword().equals(password))
        .switchIfEmpty(Mono.error(USR0002.getException()))
        .filter(User::getIsactive)
        .switchIfEmpty(Mono.error(USR0001.getException()))
        .flatMap(existingUser -> {
          existingUser.setLastlogin(LocalDateTime.now());
          return userRepository.save(existingUser).then();
        });
  }

  @Override
  public Mono<Void> disableUser(Long id) {
    return userRepository.findById(id)
        .switchIfEmpty(Mono.error(USR0004.getException()))
        .flatMap(existingUser -> {
          existingUser.setModified(LocalDateTime.now());
          existingUser.setIsactive(false);
          return userRepository.save(existingUser).then();
        });
  }

  @Override
  public Mono<String> generateAuthorizationToken(String email) {
    return userRepository.findByEmail(email)
        .switchIfEmpty(Mono.error(USR0003.getException()))
        .flatMap(user -> Mono.just(tokenProvider.generateToken(email)));
  }

  private Mono<UserDto> getPhonesFromUser(User user) {
    Mono<List<Phone>> phonesMono = phoneRepository.findByUserId(user.getId()).collectList();
    return phonesMono.map(phones -> {
      UserDto userDto = UserMapper.INSTANCE.userToUserDto(user);
      userDto.setPhones(phones);
      return userDto;
    });
  }

  private Mono<User> savePhonesFromUser(CreateUserRequest createUserRequest, User savedUser) {
    List<Phone> phones = createUserRequest.getPhones();
    if (phones != null) {
      return Flux.fromIterable(phones)
          .map(phone -> {
            phone.setUserId(savedUser.getId());
            return phone;
          })
          .flatMap(phoneRepository::save)
          .then(Mono.just(savedUser));
    }
    return Mono.just(savedUser);
  }
}
