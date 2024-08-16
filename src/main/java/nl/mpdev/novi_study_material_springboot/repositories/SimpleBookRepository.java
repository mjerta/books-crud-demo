package nl.mpdev.novi_study_material_springboot.repositories;

import nl.mpdev.novi_study_material_springboot.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "yadoken", path = "book-resource")
public interface SimpleBookRepository extends PagingAndSortingRepository<Book, Long>, CrudRepository<Book, Long> {

  List<Book> findBookByMainTitle(@Param("title") String title);


  @Override
  default void deleteAll(Iterable<? extends Book> entities) {

  }
}

