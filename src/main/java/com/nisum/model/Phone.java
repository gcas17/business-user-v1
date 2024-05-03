package com.nisum.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("phone")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Phone {
  @Id
  private Long id;
  private String number;
  @Column("citycode")
  private String cityCode;
  @Column("countrycode")
  private String countryCode;
  @Column("user_id")
  private Long userId;
}
