package api.requests.customersearchvisitor;

import api.model.CustomerSearch;

@FunctionalInterface
public interface SearchVisitor {

    void visit(CustomerSearch customerSearch);

}
