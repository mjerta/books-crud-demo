package nl.mpdev.books_crud_demo.DTO.books;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

//@JsonDeserialize
//@JsonIgnoreProperties(ignoreUnknown = false)
//@JsonRootName("BookDTO")
@Data
public class BookIncomingDTO {
  private long id;
  @NotBlank(message = "ISBN is mandatory")
  @Size(min = 10, max = 30, message = "ISBN must be between 10 and 30 characters long")
  private String isbn;
  @NotBlank(message = "Title is mandatory")
  @Size(min = 2, max = 30, message = "Title must be between 2 and 30 characters long")
  private String mainTitle;
  private Long genreId;

  public BookIncomingDTO() {
  }

}
