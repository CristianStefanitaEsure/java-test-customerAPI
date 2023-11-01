package api.model.customernodes;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CustomerEmail {

    private String email;
    private Boolean emailConfirmed;
    private String id;

}
