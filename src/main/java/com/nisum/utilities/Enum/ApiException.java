package com.nisum.utilities.Enum;

import com.nisum.utilities.Exception.CustomApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@AllArgsConstructor
@Getter
public enum ApiException {
  USR0001("USR0001", "Usuario deshabilitado", UNAUTHORIZED),
  USR0002("USR0002", "El password es incorrecto", UNAUTHORIZED),
  USR0003("USR0003", "Correo no encontrado", BAD_REQUEST),
  USR0004("USR0004", "Usuario no encontrado", BAD_REQUEST),
  USR0005("USR0005", "El correo ya fue registrado ", BAD_REQUEST),
  USR0006("USR0006", "El correo debe seguir el patron: '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$'", BAD_REQUEST),
  USR0007("USR0007", "El password no coincide con el patron definido en los properties", BAD_REQUEST);

  private final String code;
  private final String description;
  private final HttpStatus status;
  private static final List<ApiException> list = new ArrayList<>();

  private static final Map<String, ApiException> lookup = new HashMap<>();

  static {
    for (ApiException s : EnumSet.allOf(ApiException.class)) {
      list.add(s);
      lookup.put(s.getCode(), s);
    }
  }

  public static ApiException get(String code) {
    return lookup.get(code);
  }

  public CustomApiException getException() {
    return new CustomApiException(this.getDescription(), this.getStatus());
  }
}
