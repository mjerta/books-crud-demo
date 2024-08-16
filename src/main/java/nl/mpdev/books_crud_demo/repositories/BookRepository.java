package nl.mpdev.books_crud_demo.repositories;

import nl.mpdev.books_crud_demo.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository  extends JpaRepository<Book, Long> {

  Optional<Book> findByIsbnAndMainTitleAndGenre(String isbn, String mainTitle, String genre);

  boolean existsBookByIsbn(String isbn);
  boolean existsBookByMainTitle(String title);

  Book findBookByIsbn(String isbn);
  Book findBookByMainTitle(String title);


}
