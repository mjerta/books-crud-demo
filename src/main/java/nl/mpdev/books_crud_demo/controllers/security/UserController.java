package nl.mpdev.books_crud_demo.controllers.security;

import nl.mpdev.books_crud_demo.models.security.User;
import nl.mpdev.books_crud_demo.services.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping(value = "/register")
  public User registerNewUser(@RequestBody User user) {
    return userService.register(user);
  }

  @PostMapping(value = "/login")
  public String login(@RequestBody User user) {
    return userService.verify(user);
  }
}

