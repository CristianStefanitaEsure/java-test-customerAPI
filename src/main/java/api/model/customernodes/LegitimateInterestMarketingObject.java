package api.model.customernodes;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class LegitimateInterestMarketingObject {

    private String id;
    private String brand;
    private List<CustomerMarketingPermission> customerMarketingPermissions;

}
