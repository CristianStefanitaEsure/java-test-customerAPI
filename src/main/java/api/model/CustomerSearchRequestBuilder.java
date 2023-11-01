package api.model;

import api.requests.CustomerFactory;
import testdata.CustomerDataHolder;
import utils.FileLoader;

public class CustomerSearchRequestBuilder {

    private CustomerSearch customerSearchRequest;

    public CustomerSearchRequestBuilder create(String filename) {
        this.customerSearchRequest = FileLoader.asClass("json/" + filename, CustomerSearch.class);

        return this;
    }

    public CustomerSearchRequestBuilder create() {
        this.customerSearchRequest = new CustomerSearch();

        return this;
    }

    public CustomerSearchRequestBuilder setSearchFromCustomerObject(Customer customer) {
        customerSearchRequest.setFirstName(customer.getCustomerIndividualDetail().getFirstName());
        customerSearchRequest.setLastName(customer.getCustomerIndividualDetail().getLastName());
        customerSearchRequest.setPostcode(customer.getCustomerAddress().getPostcode());
        customerSearchRequest.setDateOfBirth(
            customer.getCustomerIndividualDetail().getDateOfBirth());
        customerSearchRequest.setHouseName(customer.getCustomerAddress().getHouseName());
        customerSearchRequest.setHouseNumber(customer.getCustomerAddress().getHouseNumber());

        return this;
    }

    public CustomerSearchRequestBuilder setHouseName() {
        this.customerSearchRequest.setHouseName(CustomerDataHolder.getHouseName());

        return this;
    }

    public CustomerSearchRequestBuilder setHouseNumber() {
        this.customerSearchRequest.setHouseNumber(CustomerDataHolder.getHouseNumber());

        return this;
    }

    public CustomerSearchRequestBuilder setPostCode(String postCode) {
        this.customerSearchRequest.setPostcode(postCode);

        return this;
    }

    public CustomerSearch build() {

        return customerSearchRequest;
    }


}
