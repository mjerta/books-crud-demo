package nl.mpdev.novi_study_material_springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class APIExceptionsHandler {

  @ExceptionHandler(value = APIRequestException.class)
  public ResponseEntity<Object> handleAPIRequestException(APIRequestException e) {
    HttpStatus badRequest = HttpStatus.I_AM_A_TEAPOT;
    APIException apiException = new APIException(
      e.getMessage(),
//      e,
      badRequest,
      ZonedDateTime.now(ZoneId.of("Z"))
    );
    return new ResponseEntity<>(apiException, badRequest);
  }

  public Map<String, String> handle(APIRequestException e) {
    Map<String, String> map = new HashMap<>();
    map.put("message", e.getMessage());
    map.put("HTTP status", HttpStatus.I_AM_A_TEAPOT.name());
    map.put("timestamp", String.valueOf(ZonedDateTime.now(ZoneId.of("Z"))));

    return map;
  }

}
