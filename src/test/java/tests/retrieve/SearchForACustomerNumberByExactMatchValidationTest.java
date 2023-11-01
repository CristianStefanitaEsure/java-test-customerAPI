package tests.retrieve;

import api.model.Customer;
import api.model.CustomerRequestBuilder;
import api.model.CustomerSearch;
import api.requests.BodyFactory;
import api.requests.CustomerClient;
import api.requests.JsonCustomerRequestType;
import api.requests.SearchFactory;
import api.requests.customersearchvisitor.AllDetailsSearchVisitor;
import api.requests.customersearchvisitor.IncorrectHouseNameSearchVisitor;
import api.requests.customersearchvisitor.IncorrectHouseNumberSearchVisitor;
import assertions.SearchAssertions;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testdata.CustomerDataHolder;

import static logging.Steps.*;

@Slf4j
public class SearchForACustomerNumberByExactMatchValidationTest {

    private BodyFactory bodyFactory = new BodyFactory();

    @BeforeEach
    public void init() {
        CustomerDataHolder.generateRandomDynamicTestData();
    }

    @Test
    public void customerSearchWithIncorrectHouseName() {
        given("there is an existing customer in the system");
        Customer customerBody = new CustomerRequestBuilder().createFullCustomerWithAllFields()
                .build();
        CustomerClient.createCustomer(customerBody);

        and("it is possible to search for the customer");
        CustomerSearch searchBody = SearchFactory.create(
                JsonCustomerRequestType.CUSTOMER_SEARCH);
        searchBody.accept(new AllDetailsSearchVisitor());
        CustomerClient.searchCustomerUntilSuccessOrTimeout(searchBody);

        when("the user performs search for a customer with incorrect house name");
        searchBody.accept(new IncorrectHouseNameSearchVisitor());
        SearchAssertions assertions = new SearchAssertions(
                CustomerClient.searchForACustomerNumberByExactMatch(
                        searchBody));

        then("the response will be empty");
        assertions.assertThatResponseIsEmpty();
    }

    @Test
    public void customerSearchWithIncorrectHouseNumber() {
        given("there is an existing customer in the system");
        Customer customerBody = new CustomerRequestBuilder().createFullCustomerWithAllFields()
                .build();
        CustomerClient.createCustomer(customerBody);

        and("it is possible to search for the customer");
        CustomerSearch searchBody = SearchFactory.create(
                JsonCustomerRequestType.CUSTOMER_SEARCH);
        searchBody.accept(new AllDetailsSearchVisitor());
        CustomerClient.searchCustomerUntilSuccessOrTimeout(searchBody);

        when("the user performs search for a customer with incorrect house number");
        searchBody.accept(new IncorrectHouseNumberSearchVisitor());
        SearchAssertions assertions = new SearchAssertions(
                CustomerClient.searchForACustomerNumberByExactMatch(
                        searchBody));

        then("the response will be empty");
        assertions.assertThatResponseIsEmpty();
    }

    @Test
    public void searchCustomerWithIncorrectDetails() {
        when("a user sends POST request to retrieve customer id with incorrect details");
        SearchAssertions assertions = new SearchAssertions(
                CustomerClient.searchForACustomerNumberByExactMatch(
                        bodyFactory.noResultsCustomerSearchBody()));

        then("a user will receive empty response results");
        assertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
        assertions.assertThatResponseIsEmpty();
    }
}
