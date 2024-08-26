package nl.mpdev.books_crud_demo.repositories;

import nl.mpdev.books_crud_demo.models.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
