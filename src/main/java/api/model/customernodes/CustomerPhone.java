package api.model.customernodes;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Builder
@Accessors(chain = true)
public class CustomerPhone {
    public CustomerPhone() {
    }

    public CustomerPhone(String countryCode, Boolean phoneConfirmed, String phoneType, String phoneNumber) {
        this.countryCode = countryCode;
        this.phoneConfirmed = phoneConfirmed;
        this.phoneType = phoneType;
        this.phoneNumber = phoneNumber;
    }

    private String countryCode;
    private Boolean phoneConfirmed;
    private String phoneType;
    private String phoneNumber;

}
