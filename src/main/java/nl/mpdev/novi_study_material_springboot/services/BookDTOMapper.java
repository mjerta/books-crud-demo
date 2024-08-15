package nl.mpdev.novi_study_material_springboot.services;

import nl.mpdev.novi_study_material_springboot.DTO.Book;
import nl.mpdev.novi_study_material_springboot.models.BookDTO;
import org.springframework.stereotype.Service;

@Service
public class BookDTOMapper {

  public BookDTO toDto(Book book) {
    BookDTO bookDTO = new BookDTO();
    bookDTO.setId(book.getId());
    bookDTO.setIsbn(book.getIsbn());
    bookDTO.setMainTitle(book.getMainTitle());
    return  bookDTO;
  }

  public Book toEntity(BookDTO bookDTO) {
    Book book = new Book();
    book.setId(bookDTO.getId());
    book.setIsbn(bookDTO.getIsbn());
    book.setMainTitle(bookDTO.getMainTitle());
    return book;
  }

}
