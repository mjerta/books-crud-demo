package nl.mpdev.books_crud_demo.models.security;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
  @Id
  @Column(nullable = false, unique = true)
  private String username;
  @Column(nullable = false)
  private String password;
  @Column(nullable = false)
  private boolean enabled = true;
  @OneToMany(
    targetEntity = Authority.class,
    mappedBy = "username",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.EAGER)
  private Set<Authority> authorities = new HashSet<>();

}
