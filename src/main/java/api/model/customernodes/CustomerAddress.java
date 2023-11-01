package api.model.customernodes;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CustomerAddress {

  private String country;
  private String county;
  private String houseName;
  private String houseNumber;
  private String locality;
  private String postalTown;
  private String postcode;
  private String street;
  private String subPremises;

}
