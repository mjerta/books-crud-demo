package nl.mpdev.books_crud_demo.DTO.books;

import nl.mpdev.books_crud_demo.DTO.genres.GenreDTOMapper;
import nl.mpdev.books_crud_demo.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookDTOMapper {

  @Autowired
  private GenreDTOMapper genreDTOMapper;

//  public BookIncomingDTO toDto(Book book) {
//    BookIncomingDTO bookDTO = new BookIncomingDTO();
//    bookDTO.setId(book.getId());
//    bookDTO.setIsbn(book.getIsbn());
//    bookDTO.setMainTitle(book.getMainTitle());
//    if (book.getGenre() != null) {
//      bookDTO.setGenreId(book.getGenre().getId());
//    }
//    // bookDTO.setGenreDTO(genreDTOMapper.toDTO(book.getGenre()));
//    return bookDTO;
//  }

  public Book toEntity(BookIncomingDTO bookDTO) {
    Book book = new Book();
    book.setId(bookDTO.getId());
    book.setIsbn(bookDTO.getIsbn());
    book.setMainTitle(bookDTO.getMainTitle());

    // aS you can notice the genreid is not being put within mapper
    // If I had to do that i had to add the repository to find the id of the genre id
    // and i want keep proper converns of seperations

    // book.setGenre(genreDTOMapper.toEntity(bookDTO.getGenreDTO()));
    return book;
  }

  public BookReturnDTO toBookReturnDTO(Book book) {
    BookReturnDTO bookReturnDTO = new BookReturnDTO();
    bookReturnDTO.setIsbn(book.getIsbn());
    bookReturnDTO.setMainTitle(book.getMainTitle());
    if (book.getGenre() != null) {
      bookReturnDTO.setGenreId(book.getGenre().getId());
    }
    return bookReturnDTO;
  }

}
