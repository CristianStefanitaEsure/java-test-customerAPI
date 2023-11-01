package api.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum JsonCustomerRequestType implements JsonType {

    FULL_CUSTOMER("json/createfullcustomer.json"),
    CUSTOMER_SEARCH("json/customersearch.json");

    private final String json;

    @Override
    public String json() {
        return this.json;
    }
}
