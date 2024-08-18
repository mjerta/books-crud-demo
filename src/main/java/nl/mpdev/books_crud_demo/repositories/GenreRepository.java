package nl.mpdev.books_crud_demo.repositories;

import nl.mpdev.books_crud_demo.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
