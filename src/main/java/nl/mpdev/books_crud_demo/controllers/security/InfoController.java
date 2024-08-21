package nl.mpdev.books_crud_demo.controllers.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

  @GetMapping(value = "/info")
  public String info() {
    return "This is the info page";
  }

}
