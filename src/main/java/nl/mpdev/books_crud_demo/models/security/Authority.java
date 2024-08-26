package nl.mpdev.books_crud_demo.models.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Data
@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {
  @Id
  @Column(nullable = false)
  @NotBlank
  private String username;
  @Id
  @Column(nullable = false)
  @NotBlank
  private String authority;

}