package nl.mpdev.books_crud_demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity // Is from JPA. Is used to translate java en spring code to sql.
// Hibernate is an ORM: Object Relational Mapping.
// Hibernate is taking care of the implementation
@Table(name = "books") // Is used to for example to change the table name.
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // When you not use the parameters it defaults to strategy = GenerationType.AUTO
  private long id;
  @Column(unique = true, length = 30, nullable = false)
  private String isbn;
  @Column(unique = true, length = 30, nullable = false)
  private String mainTitle;
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "genre_id", referencedColumnName = "id")
  private Genre genre;
  private String author;

  public Book() {

  }

}
