package api.model.customernodes;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CustomerIndividualDetail {

  private String carsInHousehold;
  private String dateOfBirth;
  private String firstName;
  private String genderCd;
  private String lastName;
  private String maritalStatus;
  private String numberOfChildren;
  private String residencyStatus;
  private String title;

  //oher fields
  /*
          "associateEmployments": false,
        "deceased": false,
        "registryEntityNumber": null,
        "associateBusinessEntity": null,
        "extensionFields": {},
        "deathDate": null,
        "registryTypeId": "geroot://Customer/INDIVIDUALCUSTOMER//c8259266-c8ad-32ed-8cf5-f208d5329bc3",
        "associateProviders": false,
   */

}
