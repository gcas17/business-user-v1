package com.nisum.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ApplicationProperties {

  @Value("${password.regex}")
  private String passwordRegex;
}
