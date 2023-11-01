package api.model.advancedsearchnodes;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Query {

  private List<String> firstNameIgnoreCase;
  private List<String> lastNameIgnoreCase;
  private State state;

}
