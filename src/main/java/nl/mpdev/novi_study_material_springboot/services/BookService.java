package nl.mpdev.novi_study_material_springboot.services;

import jakarta.persistence.EntityNotFoundException;
import nl.mpdev.novi_study_material_springboot.models.Book;
import nl.mpdev.novi_study_material_springboot.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {

  private final BookRepository bookRepository;

  @Autowired // This one is really needed because Spring automatically detects and injects dependencies via this constructor.
  //  However, if there are multiple constructors, you must use @Autowired to specify which constructor should be used for injection.
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public Book getBook(long id) {
    // Im using orElse fluent style that trows an exception
    Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No book found"));
    return book;
  }

  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  public Book addBook(Book newBook) {
    return bookRepository.save(newBook);
  }

  public void deleteBook(long id) {
    if (!bookRepository.existsById(id)) {
      throw new EntityNotFoundException("No book found");
    }
    bookRepository.deleteById(id);
  }

  public void deleteAllBooks() {
    bookRepository.deleteAll();
  }

  public Book updateBook(long id, Book updatedBook) {
    return bookRepository.findById(id).map(book -> {
      // Update fields
      book.setMainTitle(updatedBook.getMainTitle());
      book.setIsbn(updatedBook.getIsbn());
      book.setGenre(updatedBook.getGenre());
      return bookRepository.save(book);

      // The orElseGet eill create a new record if requested id is not being found
    }).orElseGet(() -> bookRepository.save(updatedBook));
  }

}
