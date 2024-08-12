package nl.mpdev.novi_study_material_springboot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.context.annotation.Bean;

import java.util.function.Supplier;

@Data
@Entity // Is from hibernate. Is used to translate java en spring code to sql.
// Hibernate is an ORM: Object Relational Mapping.
@Table(name = "books") // Is used to for example to change the table name.
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // When you not use the parameters it defaults to strategy = GenerationType.AUTO
  private long id;
  private String isbn;
  @NotBlank(message = "Title is mandatory")
  @Column(name = "title") // Maps to the "title" column in the database
  private String mainTitle;
  private String genre;

}
