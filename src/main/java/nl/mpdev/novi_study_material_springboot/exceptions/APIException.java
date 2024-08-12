package nl.mpdev.novi_study_material_springboot.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Map;

@Getter
public class APIException {
  private final String message;
  private final HttpStatus httpStatus;
  private final ZonedDateTime timestamp;
  private Map<String, String> errors;

  public APIException(String message,
                      HttpStatus httpStatus,
                      ZonedDateTime timestamp) {
    this.message = message;
    this.httpStatus = httpStatus;
    this.timestamp = timestamp;
  }

  public APIException(String message,
                      HttpStatus httpStatus,
                      ZonedDateTime timestamp,
                      Map errors) {
    this.message = message;
    this.httpStatus = httpStatus;
    this.timestamp = timestamp;
    this.errors = errors;
  }

}
