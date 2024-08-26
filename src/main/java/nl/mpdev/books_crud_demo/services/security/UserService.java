package nl.mpdev.books_crud_demo.services.security;

import nl.mpdev.books_crud_demo.exceptions.APIRequestException;
import nl.mpdev.books_crud_demo.models.security.User;
import nl.mpdev.books_crud_demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User register(User user) {
    if (user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
      throw new APIRequestException("User must have at least one authority.");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }
}
