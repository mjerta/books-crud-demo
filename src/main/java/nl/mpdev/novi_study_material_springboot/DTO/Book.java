package nl.mpdev.novi_study_material_springboot.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity // Is from hibernate. Is used to translate java en spring code to sql.
// Hibernate is an ORM: Object Relational Mapping.
@Table(name = "books", uniqueConstraints = {
  @UniqueConstraint(columnNames = {"isbn", "title"})
}) // Is used to for example to change the table name.
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // When you not use the parameters it defaults to strategy = GenerationType.AUTO
  private long id;
  @Column(unique = true)
  private String isbn;
  @NotBlank(message = "Title is mandatory")
  @Column(name = "title", unique = true) // Maps to the "title" column in the database
  private String mainTitle;
  private String genre;
  private String author;

  public Book() {

  }

}
