package api.requests.customervisitor;

import api.model.Customer;
import testdata.CustomerDataHolder;
import static testdata.CustomerDataHolder.getHouseName;

public class FullCustomerVisitor implements CustomerVisitor {

    @Override
    public void visit(Customer customer) {
        customer
            .getCustomerAddress()
            .setHouseName(getHouseName())
            .setHouseNumber(CustomerDataHolder.getHouseNumber());
        customer
            .getCustomerIndividualDetail()
            .setFirstName(CustomerDataHolder.getFirstName())
            .setLastName(CustomerDataHolder.getSurname());
    }
}
