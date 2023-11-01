package api.requests.customervisitor;

import api.model.Customer;
import testdata.CustomerDataHolder;

public class FullCustomerNoHouseNumberVisitor implements CustomerVisitor {

    @Override
    public void visit(Customer customer) {
        customer
            .getCustomerAddress()
            .setHouseNumber(null)
            .setHouseName(CustomerDataHolder.getHouseName());
        customer
            .getCustomerIndividualDetail()
            .setFirstName(CustomerDataHolder.getFirstName())
            .setLastName(CustomerDataHolder.getSurname());
    }
}
