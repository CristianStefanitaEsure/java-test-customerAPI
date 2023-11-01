package api.model;

import api.requests.JsonConverter;
import api.requests.customersearchvisitor.SearchVisitor;
import api.requests.customervisitor.CustomerVisitor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CustomerSearch {

    private String dateOfBirth;
    private String firstName;
    private String houseName;
    private String houseNumber;
    private String lastName;
    private String postcode;

    public void accept(SearchVisitor visitor) {
        visitor.visit(this);
    }

    public String toJsonString() {
        return JsonConverter.toJson(this);
    }

}
