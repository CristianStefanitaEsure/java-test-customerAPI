package api.requests.customervisitor;

import api.model.Customer;

@FunctionalInterface
public interface CustomerVisitor {

    void visit(Customer customer);

}
