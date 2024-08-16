package nl.mpdev.books_crud_demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name ="genre")

public class Genre {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true, length = 20)
  @Size(min = 2, max = 20)
  @NotBlank
  private String GenreType;

  public Genre() {
  }
}
