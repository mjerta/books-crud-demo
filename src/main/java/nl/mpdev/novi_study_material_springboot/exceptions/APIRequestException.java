package nl.mpdev.novi_study_material_springboot.exceptions;

public class APIRequestException extends RuntimeException {
  public APIRequestException(String message) {
    super(message);
  }

  public APIRequestException(String message, Throwable cause) {
    super(message, cause);
  }
}
