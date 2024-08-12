package nl.mpdev.novi_study_material_springboot.repositories;

import nl.mpdev.novi_study_material_springboot.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository  extends JpaRepository<Book, Long> {

}
