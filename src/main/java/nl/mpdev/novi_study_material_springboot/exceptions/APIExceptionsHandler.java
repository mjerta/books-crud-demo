package nl.mpdev.novi_study_material_springboot.exceptions;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@ControllerAdvice
public class APIExceptionsHandler {

  private final View error;

  public APIExceptionsHandler(View error) {
    this.error = error;
  }

  // When an entity being post/put is not valid
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException ex) {
    HttpStatus status = HttpStatus.I_AM_A_TEAPOT;
    Map<String, String> errors = new HashMap<>();
    if (ex.getCause() != null) {
      errors.put("Cause", ex.getCause().getMessage());
      errors.put("Thrown by", ex.getCause().getClass().getName());
    }
    errors.put("Exception class being trown:", ex.getClass().getName());
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    APIException apiException = new APIException(
      "Validation Failed",
      status,
      ZonedDateTime.now(ZoneId.of("Z")),
      errors
    );
    return new ResponseEntity<>(apiException, status);
  }

  // UNIQUE COLUMN
  // When there is a JDBC problem like a duplicate it will be catched by this exceptions. In my service layer im preventing to go into
  // this exceptions.
  // Instead im handling it with just quries being runned and validation direcltly in the service layer.
  @ExceptionHandler(value = DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleConstraintViolationExceptions(DataIntegrityViolationException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    Map<String, String> errors = new HashMap<>();

    if (ex.getCause() != null) {
      errors.put("Cause", ex.getCause().getMessage());
      errors.put("Thrown by", ex.getCause().getClass().getName());
    }
    errors.put("Exception class being trown:", ex.getClass().getName());
    APIException apiException = new APIException(
      "Validation Failed ",
      status,
      ZonedDateTime.now(ZoneId.of("Z")),
      errors
    );
    return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
  }

  // NO RECORD
  // When an entity is not found
  @ExceptionHandler(value = {EntityNotFoundException.class})
  public ResponseEntity<Object> handleEntityNotFoundExceptions(EntityNotFoundException ex) {
    Map<String, String> errors = new HashMap<>();
    HttpStatus status = HttpStatus.NOT_FOUND;
    errors.put("message", ex.getMessage());
    if (ex.getCause() != null) {
      errors.put("Cause", ex.getCause().getMessage());
      errors.put("Thrown by", ex.getCause().getClass().getName());
    }
    errors.put("Exception class being trown:", ex.getClass().getName());
    APIException apiException = new APIException(
      "Not Found validation",
      status,
      ZonedDateTime.now(ZoneId.of("Z")),
      errors
    );
    return new ResponseEntity<>(apiException, status);
  }

  // GENERAL USE
  // Some more general API exception
  @ExceptionHandler(APIRequestException.class)
  public ResponseEntity<Object> handleAPIRequestException(APIRequestException ex) {
    Map<String, String> errors = new LinkedHashMap<>();
    HttpStatus status = HttpStatus.BAD_REQUEST;
    errors.put("message", ex.getMessage());
    if (ex.getList() != null) {
      errors.putAll(ex.getList());
    }
    if (ex.getCause() != null) {
      errors.put("Cause", ex.getCause().getMessage());
      errors.put("Thrown by", ex.getCause().getClass().getName());
    }
    errors.put("Exception class being trown:", ex.getClass().getName());
    APIException apiException = new APIException(
      "API Request Error",
      status,
      ZonedDateTime.now(ZoneId.of("Z")),
      errors
    );
    return new ResponseEntity<>(apiException, status);
  }

  // ROOT EXCEPTION
  // Runtime error that are being catched - this will fire when all other exceptions not being thrown
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Object> handleGlobalException(RuntimeException ex) {
    Map<String, String> errors = new HashMap<>();
    HttpStatus status = HttpStatus.BAD_REQUEST;
    errors.put("message", "An unexpected error occurred: " + ex.getMessage());
    if (ex.getCause() != null) {
      errors.put("Cause", ex.getCause().getMessage());
      errors.put("Thrown by", ex.getCause().getClass().getName());
    }
    errors.put("Exception class being trown:", ex.getClass().getName());
    APIException apiException = new APIException(
      "Root Exceptions RunTimeExceptions",
      status,
      ZonedDateTime.now(ZoneId.of("Z")),
      errors
    );
    return new ResponseEntity<>(apiException, status);
  }


  // INVALID PROPERTIES
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    Map<String, String> errors = new HashMap<>();
    HttpStatus status = HttpStatus.BAD_REQUEST;
    errors.put("message", "An unexpected error occurred: " + ex.getMessage());
    if (ex.getCause() != null) {
      errors.put("Cause", ex.getCause().getMessage());
      errors.put("Thrown by", ex.getCause().getClass().getName());
    }
    errors.put("Exception class being trown:", ex.getClass().getName());
    APIException apiException = new APIException(
      "Invalid property validation",
      status,
      ZonedDateTime.now(ZoneId.of("Z")),
      errors
    );
    return new ResponseEntity<>(apiException, status);
  }

}
