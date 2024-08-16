package nl.mpdev.books_crud_demo.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class APIRequestException extends RuntimeException {
  private Map<String, String> list;

  public APIRequestException(String message) {
    super(message);

  }

  public HttpStatusCode getStatusCode() {
    return HttpStatus.BAD_REQUEST;
  }

  public APIRequestException(Map<String, String> list) {
    super("Duplicate books found");
    this.list = list;
  }

  public APIRequestException(String message, Throwable cause) {
    super(message, cause);
  }

}
