package nl.mpdev.books_crud_demo.controllers;

import nl.mpdev.books_crud_demo.DTO.genres.GenreDTO;
import nl.mpdev.books_crud_demo.DTO.genres.GenreDTOMapper;
import nl.mpdev.books_crud_demo.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class GenreController {


  public final GenreService genreService;
  public final GenreDTOMapper  genreDTOMapper;

  @Autowired

  public GenreController(GenreService genreService, GenreDTOMapper genreDTOMapper) {
    this.genreService = genreService;
    this.genreDTOMapper = genreDTOMapper;
  }

  @PostMapping(value = "/genre/")
  public ResponseEntity<GenreDTO> addGenre(@RequestBody GenreDTO genreDTO) {
    return ResponseEntity.ok().body(genreService.addGenre(genreDTOMapper.toEntity(genreDTO)));

  }
}
