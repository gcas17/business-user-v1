package com.nisum.utilities.Exception;

import org.springframework.http.HttpStatus;

public class CustomApiException extends RuntimeException {
  private final String message;
  private final HttpStatus httpStatus;

  public CustomApiException(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
