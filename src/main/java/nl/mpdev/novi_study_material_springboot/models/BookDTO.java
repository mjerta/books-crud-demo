package nl.mpdev.novi_study_material_springboot.models;

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
