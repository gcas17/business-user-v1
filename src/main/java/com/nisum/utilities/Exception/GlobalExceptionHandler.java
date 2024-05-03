package com.nisum.utilities.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseStatusExceptionHandler {

  @ExceptionHandler(CustomApiException.class)
  public ResponseEntity<ErrorMessage> handleCustomApiException(CustomApiException ex, WebRequest webRequest) {
    ErrorMessage validationResponse = new ErrorMessage(ex.getMessage());
    return new ResponseEntity<>(validationResponse, ex.getHttpStatus());
  }
}