package nl.mpdev.books_crud_demo.services.security;

import nl.mpdev.books_crud_demo.exceptions.APIRequestException;
import nl.mpdev.books_crud_demo.models.security.User;
import nl.mpdev.books_crud_demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JWTService jwtService;

  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  public User register(User user) {
    if (user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
      throw new APIRequestException("User must have at least one authority.");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public String verify(User user) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

    if (authentication.isAuthenticated()) {
      Map<String, Object> extraClaims = new HashMap<>();
      extraClaims.put("authorities", authentication.getAuthorities());
      return jwtService.generateToken(extraClaims, user.getUsername());

    }
    return "User is not logged in.";
  }
}
