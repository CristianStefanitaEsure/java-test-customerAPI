package tests.retrieve;

import api.requests.BodyFactory;
import api.requests.CustomerClient;
import api.requests.help.JsonRequest;
import assertions.CustomerAssertions;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testdata.CustomerDataHolder;
import utils.ResponseUtils;
import utils.StringUtils;

import static logging.Steps.*;
import static utils.EndpointsConfigurationData.*;

@Slf4j
public class RetrieveCustomerByEmailValidationTest {

    private BodyFactory bodyFactory = new BodyFactory();

    @BeforeEach
    public void init() {
        CustomerDataHolder.generateRandomDynamicTestData();
    }

    @Test
    public void searchForNonExistingEmailAddress() {
        String emailAddress = StringUtils.generateRandomEmail();

        when(" a user sends request to retrieve a customer with nonExisting email");
        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.getCustomerByEmail(emailAddress));

        then(" customerId will not be present");
        customerAssertions.assertThatResponseIsEmpty();
    }

    @Test
    public void searchingForEmailAddressUsingWildcard() {
        String emailAddress = StringUtils.generateRandomEmail();

        given("there is an existing customer");
        ResponseUtils
                .extractCustomerNumber(
                        CustomerClient.createCustomer(bodyFactory.customerWithModifiedEmail(emailAddress)));

        when(
                " a user sends request to retrieve a customer with existing email but with wildcard inside");
        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.getCustomerByEmail(
                        emailAddress.replace(emailAddress.substring(5, 10), "*")));

        then(" customerId will not be present in the results");
        customerAssertions.assertThatResponseIsEmpty();
    }

    @Test
    public void searchUsingWildcardOnly() {
        when(" a user sends request to retrieve a customer using only wildcard character");
        CustomerAssertions customerAssertions = new CustomerAssertions(
                JsonRequest.get(
                        CUSTOMER_API_URL + CUSTOMERS_PATH + BY_EMAIL_SEARCH + "*"));

        then(" results will return empty results list");
        customerAssertions.assertResponseReceivedExpectedCode(HttpStatus.SC_OK)
                .assertThatResponseIsEmpty();
    }
}
