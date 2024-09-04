package nl.mpdev.books_crud_demo.services;

import nl.mpdev.books_crud_demo.DTO.books.BookDTOMapper;
import nl.mpdev.books_crud_demo.DTO.books.BookReturnGenreDTO;
import nl.mpdev.books_crud_demo.DTO.books.BookReturnDTO;
import nl.mpdev.books_crud_demo.exceptions.APIRequestException;
import nl.mpdev.books_crud_demo.models.Book;
import nl.mpdev.books_crud_demo.DTO.books.BookIncomingDTO;
import nl.mpdev.books_crud_demo.models.Genre;
import nl.mpdev.books_crud_demo.repositories.BookRepository;
import nl.mpdev.books_crud_demo.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

  private final BookRepository bookRepository;
  private final BookDTOMapper bookDTOMapper;
  private final GenreRepository genreRepository;

  @Autowired // This one is really needed because Spring automatically detects and injects dependencies via this constructor.
  //  However, if there are multiple constructors, you must use @Autowired to specify which constructor should be used for injection.
  public BookService(BookRepository bookRepository, BookDTOMapper bookDTOMapper, GenreRepository genreRepository) {
    this.bookRepository = bookRepository;
    this.bookDTOMapper = bookDTOMapper;
    this.genreRepository = genreRepository;
  }

  public BookReturnDTO getBook(long id) {
    // Im using orElse fluent style that trows an exception
    Book book = bookRepository.findById(id).orElseThrow(() -> new APIRequestException("No Book found"));
    return bookDTOMapper.toBookReturnDTO(book);
  }

  public List<BookReturnDTO> getAllBooks() {
    List<Book> books = bookRepository.findAll();
    return books.stream().map(bookDTOMapper::toBookReturnDTO).collect(Collectors.toList());
  }

  public List<BookReturnGenreDTO> getBookGenres() {
    return bookRepository.findGenres();
  }

  public BookReturnDTO addBook(BookIncomingDTO bookDTO) {

    // Im using the approach of putting the conversion in the service layer. This way I can make sure that I can set the GenreID on the book Entity
    // Otherwise i had to do this conversion inside the mapper what would i try to prevent to inject the repository in the mapper as well.
    // Also that way I could have used conversion in the controller as well.  But actually I like to have everythinbg seperate. So only DTO are coming to the controller and DTO are going out of the controller.
    Book book = bookDTOMapper.toEntity(bookDTO);
    Map<String, String> foundBook = new HashMap<>();
    if (bookDTO.getGenreId() != null) {
      Genre genre = genreRepository.findById(bookDTO.getGenreId()).orElseThrow(() -> new APIRequestException("No Genre found"));
      book.setGenre(genre);
    }
    // Look for existing records( this will make sure the no duplicate are being inserted)
    if (bookRepository.existsBookByIsbn(book.getIsbn())) {
      foundBook.put("ISBN", bookRepository.findBookByIsbn(book.getIsbn()).getIsbn());
    }
    if (bookRepository.existsBookByMainTitle(book.getMainTitle())) {
      foundBook.put("Title", bookRepository.findBookByMainTitle(book.getMainTitle()).getMainTitle());
    }
    if (!foundBook.isEmpty()) {
      throw new APIRequestException(foundBook);
    }
    return bookDTOMapper.toBookReturnDTO(bookRepository.save(book));
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

  public BookReturnDTO updateBook(long id, BookIncomingDTO updatedBook) {
    Book book = bookDTOMapper.toEntity(updatedBook);
    if (updatedBook.getGenreId() != null) {
      Genre genre = genreRepository.findById(updatedBook.getGenreId()).orElseThrow(() -> new APIRequestException("No Genre found"));
      book.setGenre(genre);
    }
    return bookRepository.findById(id).map(bookfound -> {
      // Update fields
      bookfound.setIsbn(book.getIsbn());
      bookfound.setMainTitle(book.getMainTitle());
      if (book.getGenre() != null) {
        bookfound.setGenre(book.getGenre());
      }
      return bookDTOMapper.toBookReturnDTO(bookRepository.save(bookfound));

      // The orElseGet will create a new record if requested id is not being found
    }).orElseGet(() -> {
      Map<String, String> foundBook = new HashMap<>();
      // Look for existing records( this will make sure the no duplicate are being inserted)
      if (bookRepository.existsBookByIsbn(book.getIsbn())) {
        foundBook.put("ISBN", bookRepository.findBookByIsbn(book.getIsbn()).getIsbn());
      }
      if (bookRepository.existsBookByMainTitle(book.getMainTitle())) {
        foundBook.put("Title", bookRepository.findBookByMainTitle(book.getMainTitle()).getMainTitle());
      }
      if (!foundBook.isEmpty()) {
        throw new APIRequestException(foundBook);
      }
        return bookDTOMapper.toBookReturnDTO(bookRepository.save(book));
      }
    );
  }

  // Method to simulate an triggernull expecetion
  public void triggerNullPointerException() {
    String str = null;
    // Attempt to call a method on a null object
    int length = str.length(); // This line will throw NullPointerException
  }

}
