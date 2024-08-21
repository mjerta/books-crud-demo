package nl.mpdev.books_crud_demo.models.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "authorities")
public class Authority implements Serializable {
  @Id
  @Column(nullable = false)
  private String username;
  @Id
  @Column(nullable = false)
  private String authority;
}