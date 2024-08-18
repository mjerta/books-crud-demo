package nl.mpdev.books_crud_demo.DTO.genres;

import nl.mpdev.books_crud_demo.models.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreDTOMapper {

  public GenreDTO toDTO(Genre genre) {
    GenreDTO dto = new GenreDTO();
    dto.setId(genre.getId());
    dto.setGenreType(genre.getGenreType());
    return dto;
  }

  public Genre toEntity(GenreDTO dto) {
    Genre genre = new Genre();
    genre.setId(dto.getId());
    genre.setGenreType(dto.getGenreType());
    return genre;
  }
}
