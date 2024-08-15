package nl.mpdev.novi_study_material_springboot.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

//@JsonDeserialize
//@JsonIgnoreProperties(ignoreUnknown = false)
//@JsonRootName("BookDTO")
@Data
public class BookDTO {
  private long id;
  private String isbn;
  private String mainTitle;

  public BookDTO() {
  }

}
