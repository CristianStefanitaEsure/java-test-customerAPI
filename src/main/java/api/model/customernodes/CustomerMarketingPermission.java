package api.model.customernodes;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CustomerMarketingPermission {

    private Boolean canMarket;
    private String channel;
    private String source;
    private String updated;

}
