package com.nisum.dto;

import com.nisum.model.Phone;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDto {
  private Long id;
  private LocalDateTime created;
  private LocalDateTime modified;
  private LocalDateTime lastlogin;
  private String token;
  private Boolean isactive;
  private List<Phone> phones;
}
