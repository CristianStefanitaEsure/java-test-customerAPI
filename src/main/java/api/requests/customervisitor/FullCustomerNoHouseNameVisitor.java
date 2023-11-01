package api.requests.customervisitor;

import api.model.Customer;
import testdata.CustomerDataHolder;
import static testdata.CustomerDataHolder.getHouseName;
import utils.constants.CustomerBuilderData;

public class FullCustomerNoHouseNameVisitor implements CustomerVisitor {

    @Override
    public void visit(Customer customer) {
        customer
            .getCustomerAddress()
            .setHouseName(null)
            .setHouseNumber(CustomerBuilderData.HOUSE_NUMBER);
        customer
            .getCustomerIndividualDetail()
            .setFirstName(CustomerBuilderData.CUSTOMER_INDIVIDUAL_FIRST_NAME)
            .setLastName(CustomerBuilderData.CUSTOMER_INDIVIDUAL_LASTNAME);
    }
}
