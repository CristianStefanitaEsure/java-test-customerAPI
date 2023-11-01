package api.model;

import api.model.advancedsearchnodes.Query;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class AdvancedSearchQuery {

  private Query query;
  private String enabledPhoneticSearch;
  private String text;
  private int offset;
  private int limit;

}
