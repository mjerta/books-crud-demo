package nl.mpdev.books_crud_demo.DTO.books;

import nl.mpdev.books_crud_demo.DTO.genres.GenreDTOMapper;
import nl.mpdev.books_crud_demo.models.Book;
import org.springframework.stereotype.Component;

@Component
public class BookDTOMapper {

  public BookDTO toDto(Book book) {
    BookDTO bookDTO = new BookDTO();
    bookDTO.setId(book.getId());
    bookDTO.setIsbn(book.getIsbn());
    bookDTO.setMainTitle(book.getMainTitle());
    bookDTO.setGenre((book.getGenre()));
    return  bookDTO;
  }

  public Book toEntity(BookDTO bookDTO) {
    Book book = new Book();
    book.setId(bookDTO.getId());
    book.setIsbn(bookDTO.getIsbn());
    book.setMainTitle(bookDTO.getMainTitle());
    book.setGenre(bookDTO.getGenre());
    return book;
  }

}
