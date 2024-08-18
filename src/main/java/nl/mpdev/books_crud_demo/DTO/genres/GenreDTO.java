package nl.mpdev.books_crud_demo.DTO.genres;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GenreDTO {
  private long id;
  @Column(unique = true, length = 20)
  @Size(min = 2, max = 20)
  @NotBlank
  private String genreType;

  public GenreDTO() {
  }
}
