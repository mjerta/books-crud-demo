package nl.mpdev.books_crud_demo.services;

import nl.mpdev.books_crud_demo.DTO.genres.GenreDTO;
import nl.mpdev.books_crud_demo.DTO.genres.GenreDTOMapper;
import nl.mpdev.books_crud_demo.models.Genre;
import nl.mpdev.books_crud_demo.repositories.BookRepository;
import nl.mpdev.books_crud_demo.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {

  @Autowired
  private final GenreRepository genreRepository;
  private final GenreDTOMapper genreDTOMapper;

  public GenreService(GenreRepository genreRepository, GenreDTOMapper genreDTOMapper) {
    this.genreRepository = genreRepository;
    this.genreDTOMapper = genreDTOMapper;
  }

  public GenreDTO addGenre(Genre genre) {
    return genreDTOMapper.toDTO(genreRepository.save(genre));
  }

}
