package nl.mpdev.books_crud_demo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import nl.mpdev.books_crud_demo.DTO.books.BookIncomingDTO;
import nl.mpdev.books_crud_demo.DTO.books.BookDTOMapper;
import nl.mpdev.books_crud_demo.DTO.books.BookReturnGenreDTO;
import nl.mpdev.books_crud_demo.DTO.books.BookReturnDTO;
import nl.mpdev.books_crud_demo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api")
public class BookController {

  private final BookService bookService;

  @Autowired // This one is not really needed because Spring automatically detects and injects dependencies via this constructor.
  // If you just using field injection you could use that above the field and not use a constructor or setter.
  //  However, if there are multiple constructors, you must use @Autowired to specify which constructor should be used for injection.
  public BookController(BookService bookService, BookDTOMapper bookDTOMapper) {
    this.bookService = bookService;
  }

  @GetMapping("/csrf-token")
  public CsrfToken getCsrfToken(HttpServletRequest request) {

    return request.getAttribute("_csrf") != null ? (CsrfToken) request.getAttribute("_csrf") : null;
  }

  @GetMapping(value = "/test")
  public String test(HttpServletRequest request) {
    return "Hello World" + request.getSession().getId();
  }

  @GetMapping(value = "/books/{id}")
  // Since I'm  using a String for when a book is not found im return a String object so Im using Oject generic instead of Book
  public ResponseEntity<BookReturnDTO> getBook(@PathVariable("id") long id) {
    return ResponseEntity.status(HttpStatus.FOUND).body(bookService.getBook(id));
  }

  @GetMapping(value = "/books/")
  public ResponseEntity<List<BookReturnDTO>> getAllBooks() {
    // Explanation: ResponseEntity.status(HttpStatus.OK).body(book) allows you to specify the status and then add the body in a fluent style
    return ResponseEntity.ok().body(bookService.getAllBooks());
  }

  @GetMapping(value = "/booksgenres/")
  public ResponseEntity<List<BookReturnGenreDTO>> getBookGenres() {
    return ResponseEntity.ok().body(bookService.getBookGenres());
  }

  @PostMapping(value = "/books/addbook/")
  public ResponseEntity<BookReturnDTO> addBook(@Valid @RequestBody BookIncomingDTO book) {
    return ResponseEntity.status(HttpStatus.OK).body(bookService.addBook(book));
  }

  @PutMapping("/books/{id}")
  public ResponseEntity<BookReturnDTO> updateBook(@PathVariable int id, @Valid @RequestBody BookIncomingDTO bookDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(bookService.updateBook(id, bookDTO));
  }

//  @PostMapping("/book-resource")
//  public ResponseEntity<BookDTO> addAnotherBook(@Valid @RequestBody BookDTO book) {
//    return ResponseEntity.status(HttpStatus.OK).body(bookService.addBook(bookDTOMapper.toEntity(book)));
//  }

  @DeleteMapping(value = "/books/deletebook/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable long id) {
    bookService.deleteBook(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping(value =  "/books/deletebooks/")
  public ResponseEntity<Void> deleteBooks() {
    bookService.deleteAllBooks();
    return ResponseEntity.noContent().build();
  }


//  2 examples of validation directly in the controller - which im not using. Im using globalexceptions handler
//  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
//  @ExceptionHandler(MethodArgumentNotValidException.class)
//  public Map<String, String> handleValidationExceptions(
//    MethodArgumentNotValidException ex) {
//    Map<String, String> errors = new HashMap<>();
//    ex.getBindingResult().getAllErrors().forEach((error) -> {
//      String fieldName = ((FieldError) error).getField();
//      String errorMessage = error.getDefaultMessage();
//      errors.put(fieldName, errorMessage);
//    });
//    errors.put("statuscode", ex.getStatusCode().toString());
//    return errors;
//  }

//  @ResponseStatus(HttpStatus.NOT_FOUND)
//  @ExceptionHandler(EntityNotFoundException.class)
//  public Map<String, String> handleEntityNotFoundExceptions(EntityNotFoundException ex) {
//    Map<String, String> errors = new HashMap<>();
//    errors.put("404 error", ex.getMessage());
//    return errors;
//  }
}
