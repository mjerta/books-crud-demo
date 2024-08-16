package nl.mpdev.novi_study_material_springboot.repositories;

import nl.mpdev.novi_study_material_springboot.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository  extends JpaRepository<Book, Long> {

  Optional<Book> findByIsbnAndMainTitleAndGenre(String isbn, String mainTitle, String genre);

  boolean existsBookByIsbn(String isbn);
  boolean existsBookByMainTitle(String title);

  Book findBookByIsbn(String isbn);
  Book findBookByMainTitle(String title);


}
