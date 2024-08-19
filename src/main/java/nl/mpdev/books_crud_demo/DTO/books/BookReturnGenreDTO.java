package nl.mpdev.books_crud_demo.DTO.books;

import lombok.Data;

@Data
public class BookReturnGenreDTO {
  private String mainTitle;
  private String genreType;

  public BookReturnGenreDTO(String mainTitle, String genreType) {
    this.mainTitle = mainTitle;
    this.genreType = genreType;
  }
}
