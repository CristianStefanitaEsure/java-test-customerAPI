package steps.customer.helper;

import api.model.Customer;
import api.model.CustomerRequestBuilder;
import api.requests.CustomerClient;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;


public class CustomerManager {

    public static final CustomerRequestBuilder customerRequestBuilder = new CustomerRequestBuilder();

    public static HttpResponse<JsonNode> createCustomer(Customer fullCustomer) {
        return CustomerClient.createCustomer(fullCustomer);
    }

    public static HttpResponse<JsonNode> findOrCreateWithPooling(Customer fullCustomer) {
        return CustomerClient.findOrCreateWithPooling(fullCustomer);
    }

    public static HttpResponse<JsonNode> findOrCreateWithFlag(Customer fullCustomer) {
        return CustomerClient.findOrCreateWithFlag(fullCustomer);
    }

    public static HttpResponse<JsonNode> createFullCustomerWithAllFields() {
        return createCustomer(customerRequestBuilder.createFullCustomerWithAllFields().build());
    }

    public static HttpResponse<JsonNode> createFullCustomerWithAllFieldsWithFlag() {
        return findOrCreateWithFlag(customerRequestBuilder.createFullCustomerWithAllFields().build());
    }

    public static HttpResponse<JsonNode> createCustomerWithMinimumFields() {
        return createCustomer(customerRequestBuilder.createCustomerWithRequiredInfo().build());
    }

    public static HttpResponse<JsonNode> createCustomerWithMinimumFields(Customer customerRequestBuilder) {
        return createCustomer(customerRequestBuilder);
    }

    public static HttpResponse<JsonNode> findOrCreateFullCustomerWithAllFields() {
        return findOrCreateWithPooling(customerRequestBuilder.createFullCustomerWithAllFields().build());
    }

    public static HttpResponse<JsonNode> findOrCreateCustomerWithMinimumFields() {
        return findOrCreateWithPooling(customerRequestBuilder.createCustomerWithRequiredInfo().build());
    }

    public static HttpResponse<JsonNode> createFullCustomerWithAllFieldsUpadtingEmail(String email) {
        return createCustomer(customerRequestBuilder.createFullCustomerWithAllFields().setCustomerEmailToValue(email).build());
    }

    public static Customer getCustomerRequest() {
        return customerRequestBuilder.getCustomerRequest();
    }
}
