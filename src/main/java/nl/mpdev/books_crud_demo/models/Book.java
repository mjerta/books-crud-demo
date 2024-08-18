package nl.mpdev.books_crud_demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity // Is from JPA. Is used to translate java en spring code to sql.
// Hibernate is an ORM: Object Relational Mapping.
// Hibernate is taking care of the implementation
@Table(name = "books", uniqueConstraints = {@UniqueConstraint(columnNames = {"isbn", "title"})
}) // Is used to for example to change the table name.
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // When you not use the parameters it defaults to strategy = GenerationType.AUTO
  private long id;
  @Column(unique = true, length = 13)
  @NotBlank(message = "ISBN is mandatory")
  @Size(min = 10, max = 13, message = "ISBN must be between 10 and 13 characters long")
  private String isbn;
  @NotBlank(message = "Title is mandatory")
  @Size(min = 2, max = 20, message = "Title must be between 2 and 20 characters long")
  @Column(name = "title", unique = true, length = 20) // Ensure the length matches the Size annotation
  private String mainTitle;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "genre_id", referencedColumnName = "id")
  private Genre genre;
  private String author;

  public Book() {

  }

}
