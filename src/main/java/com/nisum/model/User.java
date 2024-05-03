package com.nisum.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Table("customer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
  @Id
  private Long id;
  private String name;
  private String email;
  private String password;
  @CreatedDate
  private LocalDateTime created;
  @LastModifiedDate
  private LocalDateTime modified;
  private LocalDateTime lastlogin;
  private String token;
  private Boolean isactive;
}
