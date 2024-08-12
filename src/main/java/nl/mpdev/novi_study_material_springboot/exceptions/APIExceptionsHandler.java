package nl.mpdev.novi_study_material_springboot.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.aspectj.weaver.ast.Instanceof;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class APIExceptionsHandler {

//  @ExceptionHandler(value = APIRequestException.class)
//  public ResponseEntity<Object> handleAPIRequestException(APIRequestException e) {
//    HttpStatus badRequest = HttpStatus.I_AM_A_TEAPOT;
//    APIException apiException = new APIException(
//      e.getMessage(),
////      e,
//      badRequest,
//      ZonedDateTime.now(ZoneId.of("Z"))
//    );
//    return new ResponseEntity<>(apiException, badRequest);
//  }

//  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
//  @ExceptionHandler(value = APIRequestException.class)
//  public Map<String, String> handleAPIRequestException(APIRequestException e) {
//    Map<String, String> map = new HashMap<>();
//    map.put("message", e.getMessage());
//    map.put("HTTP status", HttpStatus.I_AM_A_TEAPOT.name());
//    map.put("timestamp", String.valueOf(ZonedDateTime.now(ZoneId.of("Z"))));
//
//    return map;
//  }

//  @ResponseStatus(HttpStatus.NOT_FOUND)
//  @ExceptionHandler(APIRequestException.class)
//  public Map<String, String> handleEntityNotFoundExeptions(APIRequestException ex) {
//    Map<String, String> errors = new HashMap<>();
//    errors.put("404 error", ex.getMessage());
//    return errors;
//  }

//  @ExceptionHandler(APIRequestException.class)
//  public ResponseEntity<Map<String, String>> handleEntityNotFoundExeptions(APIRequestException ex) {
//    Map<String, String> errors = new HashMap<>();
//    errors.put("404 error", ex.getMessage());
//
//    errors.put("HTTP", HttpStatus.I_AM_A_TEAPOT.name());
//    errors.put("TimeStamp", String.valueOf(ZonedDateTime.now(ZoneId.of("Z"))));
//    return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
//  }




  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    Map<String, String> errors = new HashMap<>();
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
    return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {EntityNotFoundException.class})
  public ResponseEntity<Object> handleEntityNotFoundExceptions(RuntimeException ex) {
    Map<String, String> errors = new HashMap<>();
    HttpStatus status = HttpStatus.NOT_FOUND;
    errors.put("message", ex.getMessage());
    APIException apiException = new APIException(
      "Not Found validation",
      status,
      ZonedDateTime.now(ZoneId.of("Z")),
      errors
    );
    return new ResponseEntity<>(apiException,status);
  }

  @ExceptionHandler(APIRequestException.class)
  public ResponseEntity<Object> handleAPIRequestException(APIRequestException ex) {
    Map<String, String> errors = new HashMap<>();
    errors.put("message", ex.getMessage());

    APIException apiException = new APIException(
      "API Request Error",
      HttpStatus.BAD_REQUEST,
      ZonedDateTime.now(ZoneId.of("Z")),
      errors
    );
    return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
  }

//  @ExceptionHandler(RuntimeException.class)
//  public ResponseEntity<Object> handleGlobalException(RuntimeException ex) {
//    Map<String, String> errors = new HashMap<>();
//    errors.put("message", "An unexpected error occurred: " + ex.getMessage());
//
//    APIException apiException = new APIException(
//      "Internal Server Error",
//      HttpStatus.INTERNAL_SERVER_ERROR,
//      ZonedDateTime.now(ZoneId.of("Z")),
//      errors
//    );
//    return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
//  }



}
