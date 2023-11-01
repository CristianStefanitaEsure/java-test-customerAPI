package tests.retrieve;

import api.model.CustomerRequestBuilder;
import api.requests.BodyFactory;
import api.requests.CustomerClient;
import assertions.EmailEndpointAssertions;
import logging.Steps;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testdata.CustomerDataHolder;
import utils.ResponseUtils;

import static utils.constants.CustomerBuilderData.generateRequiredCustomerData;

@Slf4j
public class RetrieveCustomersEmailsByCustomerNumberTest {

    private BodyFactory bodyFactory = new BodyFactory();

    @BeforeEach
    public void init() {
        CustomerDataHolder.generateRandomDynamicTestData();
        generateRequiredCustomerData();
    }

    @Test
    public void retrieveCustomerEmailForCustomerWithEmailAddress() {

        var customer = new CustomerRequestBuilder().createFullCustomerWithAllFields().build();

        Steps.given(" there is a customer with an existing email address");
        var customerResponse = ResponseUtils.parseResponseToCustomer(
                CustomerClient.createCustomer(customer));

        var customerId = customerResponse.getCustomerNumber();

        Steps.when(" there is a request to retrieve customer emails");
        EmailEndpointAssertions emailAssertions = new EmailEndpointAssertions(
                CustomerClient.getEmailByCustomerNumber(customerId));

        Steps.then(" the response will include email data sent in request");
        emailAssertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
        emailAssertions.assertEmailIsTheSameAsInRequest(customerResponse.getCustomerEmails().get(0));

    }

    @Test
    public void retrieveCustomerEmailForCustomerWithNoEmailAddress() {
        var customer = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();

        Steps.given(" there is an customer with no email address");

        var customerResponse = ResponseUtils.parseResponseToCustomer(
                CustomerClient.createCustomer(customer));

        var customerId = customerResponse.getCustomerNumber();

        Steps.when(" there is a request to retrieve customer emails");
        EmailEndpointAssertions assertions = new EmailEndpointAssertions(
                CustomerClient.getCustomerByCustomerNumber(customerId));

        Steps.then(" there will be no email in the response");
        assertions.assertEmailIsEmpty(customerResponse.getCustomerEmails());
    }
}
