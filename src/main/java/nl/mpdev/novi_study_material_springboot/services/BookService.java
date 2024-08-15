package nl.mpdev.novi_study_material_springboot.services;

import nl.mpdev.novi_study_material_springboot.exceptions.APIRequestException;
import nl.mpdev.novi_study_material_springboot.DTO.Book;
import nl.mpdev.novi_study_material_springboot.DTO.BookDTO;
import nl.mpdev.novi_study_material_springboot.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

  private final BookRepository bookRepository;
  private final BookDTOMapper bookDTOMapper;

  @Autowired // This one is really needed because Spring automatically detects and injects dependencies via this constructor.
  //  However, if there are multiple constructors, you must use @Autowired to specify which constructor should be used for injection.
  public BookService(BookRepository bookRepository, BookDTOMapper bookDTOMapper) {
    this.bookRepository = bookRepository;
    this.bookDTOMapper = bookDTOMapper;
  }

  public BookDTO getBook(long id) {
    // Im using orElse fluent style that trows an exception
    Book book = bookRepository.findById(id).orElseThrow(() -> new APIRequestException("No Book found"));
    return bookDTOMapper.toDto(book);
  }

  public List<BookDTO> getAllBooks() {
    List<Book> books = bookRepository.findAll();
    return books.stream().map(bookDTOMapper::toDto).collect(Collectors.toList());
  }

  public BookDTO addBook(BookDTO bookDTO) {
    Book newBook = bookDTOMapper.toEntity(bookDTO);
    Map<String, String> foundBook = new HashMap<>();
    if (bookRepository.existsBookByIsbn(bookDTO.getIsbn())) {
      foundBook.put("ISBN", bookRepository.findBookByIsbn(bookDTO.getIsbn()).getIsbn());
    }
    if (bookRepository.existsBookByMainTitle(bookDTO.getMainTitle())) {
      foundBook.put("Title", bookRepository.findBookByMainTitle(bookDTO.getMainTitle()).getMainTitle());
    }
    if (!foundBook.isEmpty()) {
      throw new APIRequestException(foundBook);
    }
    return bookDTOMapper.toDto(bookRepository.save(newBook));
  }

  public void deleteBook(long id) {
    if (!bookRepository.existsById(id)) {
      throw new APIRequestException("No books are found");
    }
    bookRepository.deleteById(id);
  }

  public void deleteAllBooks() {
    bookRepository.deleteAll();
  }

  public BookDTO updateBook(long id, BookDTO updatedBook) {
    Book newBook = bookDTOMapper.toEntity(updatedBook);
    return bookRepository.findById(id).map(book -> {
      // Update fields
      book.setMainTitle(updatedBook.getMainTitle());
      book.setIsbn(updatedBook.getIsbn());
      //book.setGenre(newBook.getGenre());
      return bookDTOMapper.toDto(bookRepository.save(book));

      // The orElseGet will create a new record if requested id is not being found
    }).orElseGet(() -> {
        return bookDTOMapper.toDto(bookRepository.save(newBook));
      }
    );
  }

  public void triggerNullPointerException() {
    String str = null;
    // Attempt to call a method on a null object
    int length = str.length(); // This line will throw NullPointerException
  }

}
