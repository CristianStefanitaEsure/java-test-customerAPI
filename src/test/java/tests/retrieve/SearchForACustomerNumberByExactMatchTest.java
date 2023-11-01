package tests.retrieve;

import api.model.Customer;
import api.model.CustomerRequestBuilder;
import api.model.CustomerSearch;
import api.requests.BodyFactory;
import api.requests.CustomerClient;
import api.requests.JsonCustomerRequestType;
import api.requests.SearchFactory;
import api.requests.customersearchvisitor.AllDetailsSearchVisitor;
import api.requests.customersearchvisitor.NoHouseNameSearchVisitor;
import api.requests.customersearchvisitor.NoHouseNumberSearchVisitor;
import api.requests.customervisitor.FullCustomerNoHouseNameVisitor;
import assertions.SearchAssertions;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testdata.CustomerDataHolder;
import utils.ResponseUtils;

import static logging.Steps.*;
import static utils.constants.CustomerBuilderData.generateRequiredCustomerData;

@Slf4j
public class SearchForACustomerNumberByExactMatchTest {

    private BodyFactory bodyFactory = new BodyFactory();

    @BeforeEach
    public void init() {
        CustomerDataHolder.generateRandomDynamicTestData();
        generateRequiredCustomerData();
    }

    /* Happy Tests - naming convention should be reviewed
     * */

    @Test
    public void testCustomerSearchPostRequest() {
        given("there is an existing customer in the system");
        Customer customer = new CustomerRequestBuilder().createFullCustomerWithAllFields().build();

        HttpResponse<JsonNode> createResponse = CustomerClient.createCustomer(customer);
        String customerId = ResponseUtils.extractCustomerNumber(createResponse);

        CustomerSearch searchBody = SearchFactory.create(JsonCustomerRequestType.CUSTOMER_SEARCH);
        searchBody.accept(new NoHouseNumberSearchVisitor());

        when("a user sends POST request to retrieve customer id");
        SearchAssertions assertions = new SearchAssertions(
                CustomerClient.searchCustomerUntilSuccessOrTimeout(
                        searchBody));

        then("a user will receive customer id in response body");
        assertions.assertThatResponseHasDataInResults(customerId);
    }

    @Test
    public void customerSearchWithHouseNameAndHouseNumber() {
        given("there is an existing customer in the system");
        Customer customer = new CustomerRequestBuilder().createFullCustomerWithAllFields().build();
        HttpResponse<JsonNode> createResponse = CustomerClient.createCustomer(customer);
        String customerNumber = ResponseUtils.extractCustomerNumber(createResponse);

        when("the user performs search for a customer with all details");
        CustomerSearch searchDetails = SearchFactory.create(
                JsonCustomerRequestType.CUSTOMER_SEARCH);
        searchDetails.accept(new AllDetailsSearchVisitor());
        SearchAssertions assertions = new SearchAssertions(
                CustomerClient.searchCustomerUntilSuccessOrTimeout(searchDetails));
        then("the customer number will be retrieved");
        assertions.assertThatResponseHasDataInResults(customerNumber);
    }

    @Test
    public void customerSearchWithoutHouseName() {
        given("there is an existing customer in the system with no house name in the address");
        Customer customer = new CustomerRequestBuilder().createFullCustomerWithAllFields().build();
        customer.accept(new FullCustomerNoHouseNameVisitor());
        HttpResponse<JsonNode> createResponse = CustomerClient.createCustomer(customer);
        String customerNumber = ResponseUtils.extractCustomerNumber(createResponse);

        when("the user performs search for a customer with all the required details");
        CustomerSearch searchBody = SearchFactory.create(JsonCustomerRequestType.CUSTOMER_SEARCH);
        searchBody.accept(new NoHouseNameSearchVisitor());
        SearchAssertions assertions = new SearchAssertions(
                CustomerClient.searchCustomerUntilSuccessOrTimeout(searchBody));

        then("the customer number will be retrieved");
        assertions.assertThatResponseHasDataInResults(customerNumber);
    }

    @Test
    public void customerSearchWithoutHouseNumber() {
        given("there is an existing customer in the system with no house number in the address");
        Customer customerBody = new CustomerRequestBuilder().createFullCustomerWithAllFields()
                .build();
        HttpResponse<JsonNode> createResponse = CustomerClient.createCustomer(customerBody);
        String customerNumber = ResponseUtils.extractCustomerNumber(createResponse);

        when("the user performs search for a customer with all the required details");
        CustomerSearch searchBody = SearchFactory.create(JsonCustomerRequestType.CUSTOMER_SEARCH);
        searchBody.accept(new NoHouseNumberSearchVisitor());
        SearchAssertions assertions = new SearchAssertions(
                CustomerClient.searchCustomerUntilSuccessOrTimeout(searchBody));

        then("the customer number will be retrieved");
        assertions.assertThatResponseHasDataInResults(customerNumber);
    }

}
