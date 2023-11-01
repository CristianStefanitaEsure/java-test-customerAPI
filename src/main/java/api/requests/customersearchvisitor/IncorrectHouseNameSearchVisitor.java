package api.requests.customersearchvisitor;

import api.model.Customer;
import api.model.CustomerSearch;
import api.requests.CustomerFactory;
import api.requests.JsonCustomerRequestType;
import testdata.CustomerDataHolder;
import utils.constants.CustomerBuilderData;

public class IncorrectHouseNameSearchVisitor implements SearchVisitor {

    private final String INCORRECT_NAME = "ppp";

    @Override
    public void visit(CustomerSearch customerSearch) {
        Customer customer = CustomerFactory.create(JsonCustomerRequestType.FULL_CUSTOMER);
        customerSearch
            .setFirstName(CustomerBuilderData.CUSTOMER_INDIVIDUAL_FIRST_NAME)
            .setLastName(CustomerBuilderData.CUSTOMER_INDIVIDUAL_LASTNAME)
            .setDateOfBirth(CustomerBuilderData.CUSTOMER_INDIVIDUAL_DOB)
            .setPostcode(CustomerBuilderData.POST_CODE)
            .setHouseName(INCORRECT_NAME);
    }
}
