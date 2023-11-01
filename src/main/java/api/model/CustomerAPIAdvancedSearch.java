package api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CustomerAPIAdvancedSearch {

  private String dateOfBirth;
  private String firstName;
  private String lastName;
  private String postCode;

}
