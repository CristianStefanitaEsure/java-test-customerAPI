package api.model;

import api.model.customernodes.*;
import api.requests.JsonConverter;
import api.requests.customervisitor.CustomerVisitor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Customer {
    public Customer() {
    }

    public Customer(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Customer(List<String> brands, String customerNumber) {
        this.brands = brands;
        this.customerNumber = customerNumber;
    }

    private List<String> brands;
    private CustomerAddress customerAddress;
    private List<CustomerBar> customerBars;
    private List<CustomerEmail> customerEmails;
    private List<CustomerEmployment> customerEmployments;
    private List<LegitimateInterestMarketingObject> customerLegitimateInterestMarketingPermissions;
    private CustomerIndividualDetail customerIndividualDetail;
    private String customerNumber;
    private List<CustomerPhone> customerPhones;
    private CustomerCreateUpdateWarnings customerCreateUpdateWarnings;

    public void accept(CustomerVisitor visitor) {
        visitor.visit(this);
    }

    public String toJsonString() {
        return JsonConverter.toJson(this);
    }

}
