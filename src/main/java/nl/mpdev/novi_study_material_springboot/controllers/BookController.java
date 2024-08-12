package nl.mpdev.novi_study_material_springboot.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import nl.mpdev.novi_study_material_springboot.models.Book;
import nl.mpdev.novi_study_material_springboot.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
public class BookController {

  private final BookService bookService;

  @Autowired // This one is not really needed because Spring automatically detects and injects dependencies via this constructor.
  // If you just using field injection you could use that above the field and not use a constructor or setter.
  //  However, if there are multiple constructors, you must use @Autowired to specify which constructor should be used for injection.
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping(value = "/books/{id}")
  // Since I'm  using a String for when a book is not found im return a String object so Im using Oject generic instead of Book
  public ResponseEntity<Object> getBook(@PathVariable("id") long id) {
    bookService.triggerNullPointerException();
    return ResponseEntity.status(HttpStatus.FOUND).body(bookService.getBook(id));
  }

  @GetMapping(value = "/books/")
  public ResponseEntity<List<Book>> getAllBooks() {
    // Explanation: ResponseEntity.status(HttpStatus.OK).body(book) allows you to specify the status and then add the body in a fluent style
    return ResponseEntity.ok().body(bookService.getAllBooks());
  }

  @PostMapping(value = "/books/addbook/")
  public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
    return ResponseEntity.status(HttpStatus.OK).body(bookService.addBook(book));
  }

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

  @PutMapping("books/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable int id, @Valid  @RequestBody Book book) {
    return ResponseEntity.status(HttpStatus.CREATED).body(bookService.updateBook(id, book));
  }

//  @ResponseStatus(HttpStatus.BAD_REQUEST)
//  @ExceptionHandler(MethodArgumentNotValidException.class)
//  public Map<String, String> handleValidationExceptions(
//    MethodArgumentNotValidException ex) {
//    Map<String, String> errors = new HashMap<>();
//    ex.getBindingResult().getAllErrors().forEach((error) -> {
//      String fieldName = ((FieldError) error).getField();
//      String errorMessage = error.getDefaultMessage();
//      errors.put(fieldName, errorMessage);
//    });
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
