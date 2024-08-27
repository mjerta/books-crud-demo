package nl.mpdev.books_crud_demo.services.security;

import nl.mpdev.books_crud_demo.exceptions.APIRequestException;
import nl.mpdev.books_crud_demo.models.security.User;
import nl.mpdev.books_crud_demo.models.security.UserPrincipal;
import nl.mpdev.books_crud_demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  @Autowired
  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);

    if (user == null) {
      throw new APIRequestException("User not found");
    }
    else {
      return new UserPrincipal(user);
    }
  }
}
