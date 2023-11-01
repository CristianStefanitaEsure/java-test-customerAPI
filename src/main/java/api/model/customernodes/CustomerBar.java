package api.model.customernodes;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CustomerBar {

    private String barCode;
    private String barType;
    private String category;
    private String description;
    private String endDate;
    private String policyNumber;
    private String reason;
    private String startDate;
    private String removeReason;
    private String removeOther;

}
