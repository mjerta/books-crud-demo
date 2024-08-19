package nl.mpdev.books_crud_demo.DTO.books;

import lombok.Data;

@Data
public class BookReturnDTO {

  private String isbn;
  private String mainTitle;
  private long genreId;

  public BookReturnDTO() {
  }
}
