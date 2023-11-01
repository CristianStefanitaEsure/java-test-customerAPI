package api.model.customernodes;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CustomerEmployment {

  private String employmentStatus;
  private String primaryIndustry;
  private String primaryOccupation;
  private String secondaryIndustry;
  private String secondaryOccupation;

}
