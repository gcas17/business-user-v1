package com.nisum.config;

import com.nisum.security.TokenProvider;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.util.Base64;

@Configuration
public class JwtConfig {

  @Value("${jwt.expiration}")
  private int expiration;

  @Bean
  public JwtParser jwtParser() {
    return Jwts.parser().setSigningKey(secretKey());
  }

  @Bean
  public JwtBuilder jwtBuilder() {
    return Jwts.builder().signWith(secretKey(), SignatureAlgorithm.HS512);
  }

  @Bean
  public TokenProvider tokenProvider() {
    return new TokenProvider(Base64.getEncoder().encodeToString(secretKey().getEncoded()), expiration);
  }

  private SecretKey secretKey() {
    return Keys.secretKeyFor(SignatureAlgorithm.HS512);
  }
}
