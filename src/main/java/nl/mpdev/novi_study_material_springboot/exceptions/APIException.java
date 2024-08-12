package nl.mpdev.novi_study_material_springboot.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class APIException {
  private final String message;
  private final HttpStatus httpStatus;
  private final ZonedDateTime timestamp;

  public APIException(final String message,
                      final HttpStatus httpStatus,
                      final ZonedDateTime timestamp) {
    this.message = message;
    this.httpStatus = httpStatus;
    this.timestamp = timestamp;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public ZonedDateTime getTimestamp() {
    return timestamp;
  }
}
