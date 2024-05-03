package com.nisum.dto;

import com.nisum.model.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreateUserRequest {
  private String name;
  private String email;
  private String password;
  private String token;
  private Boolean isactive;
  private List<Phone> phones;
}
