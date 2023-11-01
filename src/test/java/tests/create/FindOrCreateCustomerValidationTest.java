package tests.create;

import api.model.Customer;
import api.model.CustomerRequestBuilder;
import api.requests.BodyFactory;
import api.requests.CustomerClient;
import api.requests.DxpClient;
import api.requests.help.JsonRequest;
import assertions.CustomerAssertions;
import assertions.DxpCustomerAssertions;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import logging.Steps;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parsing.json.JsonParser;
import testdata.CustomerDataHolder;
import utils.ResponseUtils;
import utils.StringUtils;

import static logging.Steps.*;
import static utils.EndpointsConfigurationData.*;

@Slf4j
public class FindOrCreateCustomerValidationTest {

    private BodyFactory bodyFactory = new BodyFactory();

    @BeforeEach
    public void init() {
        CustomerDataHolder.generateRandomDynamicTestData();
    }

    @Test
    public void validateIfIncorrectBarsObjectWillGetRejected() {
        Customer customerBody = bodyFactory.fullCustomer();

        given(" there is existing customer");
        CustomerClient.createCustomer(
                customerBody);

        when(" the dxp is called and a mandatory field is missing");
        customerBody.getCustomerIndividualDetail().setFirstName(null);
        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.findOrCreate(customerBody));
        then(" the response will include Bad Request");
        customerAssertions.assertResponseReceivedExpectedCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void sendFindOrCreateRequestWithExistingCustomerWithSameCustomersEmail() {
        String emailAddress = StringUtils.generateRandomEmail();
        Customer customer = bodyFactory.customerWithModifiedEmail(emailAddress);

        Steps.given(" there is an existing customer");
        CustomerClient.findOrCreate(customer);

        Steps.when(" there is another customer request with the same data");
        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.findOrCreateWithPooling(customer));

        Steps.then(" retrieved data include customers email");
        customerAssertions.assertResponseResultsContainString(emailAddress);

        Steps.and(" flag for the customer email uniqueness will be true");
        customerAssertions.assertThatEmailUniquenessHasValue(true);
    }

    @Test
    public void sendFindOrCreateRequestWithExistingCustomerButOtherCustomersEmail() {
        String emailAddress = StringUtils.generateRandomEmail();
        String secondEmailAddress = StringUtils.generateRandomEmail();
        Customer customer = bodyFactory.customerWithModifiedEmail(emailAddress);
        Customer secondCustomer = bodyFactory.customerWithModifiedEmail(secondEmailAddress);
        secondCustomer.getCustomerIndividualDetail()
                .setFirstName(StringUtils.generateRandomString(10));

        Steps.given(" there are two customers with unique email addresses");
        CustomerClient.findOrCreate(customer);
        CustomerClient.findOrCreate(secondCustomer);

        Steps.when(" there is a request with second customers data, but other customers email");
        secondCustomer.getCustomerEmails().get(0).setEmail(emailAddress);
        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.findOrCreateWithPooling(secondCustomer));

        Steps.then(" data will be retrieved with second customers email address");
        customerAssertions.assertResponseResultsContainString(secondEmailAddress);

        Steps.and(" email uniqueness flag will be set to false");
        customerAssertions.assertThatEmailUniquenessHasValue(false);
    }

    @Test
    public void sendFindOrCreateRequestWithExistingCustomerButUniqueEmail() {
        String emailAddress = StringUtils.generateRandomEmail();
        Customer customer = bodyFactory.customerWithModifiedEmail(emailAddress);

        Steps.given(" there is an existing customer");
        CustomerClient.findOrCreate(customer);

        Steps.when(
                " there is another customer request with the same data but different unique email");
        customer.getCustomerEmails().get(0).setEmail(StringUtils.generateRandomEmail());
        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.findOrCreateWithPooling(customer));

        Steps.then(" retrieved data will include original customer email");
        customerAssertions.assertResponseResultsContainString(emailAddress);

        Steps.and(" flag for the customer email uniqueness will be true");
        customerAssertions.assertThatEmailUniquenessHasValue(true);

    }

    @Test
    public void sendFindOrCreateRequestWithNewCustomerAndUniqueEmail() {
        String emailAddress = StringUtils.generateRandomEmail();
        Customer customer = bodyFactory.customerWithModifiedEmail(emailAddress);

        Steps.given(" there is a request for a new customer with a unique email address");
        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.findOrCreate(customer));

        Steps.then(" customer will be created with the unique email address");
        customerAssertions.assertResponseResultsContainString(emailAddress);

        Steps.and(" the email uniqueness flag will be set to true");
        customerAssertions.assertThatEmailUniquenessHasValue(true);
    }

    @Test
    public void sendFindOrCreateRequestWithNewCustomerButDuplicateEmail() {
        String emailAddress = StringUtils.generateRandomEmail();
        Customer customer = bodyFactory.customerWithModifiedEmail(emailAddress);

        Steps.given(" there is an existing customer");
        CustomerClient.findOrCreate(customer);

        Steps.when(" a new customer request comes with an existing customer email");
        Customer newCustomer = bodyFactory.customerWithModifiedEmail(emailAddress);
        newCustomer.getCustomerIndividualDetail().setLastName(StringUtils.generateRandomString(10));
        CustomerAssertions assertions = new CustomerAssertions(
                CustomerClient.findOrCreateWithPooling(newCustomer));

        Steps.then(" a new customer will be created without any email");
        assertions.assertResponseReceivedExpectedCode(HttpStatus.SC_OK);
        assertions.assertNoEmailsPresentForCustomer();

        Steps.and(" the email uniqueness flag will be st to false");
        assertions.assertThatEmailUniquenessHasValue(false);
    }

    @Test
    public void validateIfPcwFlagAcceptsOnlyProperValues() {
        var requestFlag = "?isPCWQuoteRequest=bad";
        var customer = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();

        Steps.when(
                " there is a request to find-or-create customer with incorrect value for the isPCWQuoteRequest flag");
        CustomerAssertions customerAssertions = new CustomerAssertions(
                JsonRequest.post(CUSTOMER_API_URL + CUSTOMERS_PATH
                                + FIND_OR_CREATE + requestFlag,
                        JsonParser.classToJsonString(customer)));

        customerAssertions.assertResponseReceivedExpectedCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void falsePcwFlag() {
        var requestFlag = "?isPCWQuoteRequest=false";
        Customer customer = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();

        given(
                " there is a request to create customer using find-or-create endpoint with isPCWQuoteRequest set to true");
        HttpResponse<JsonNode> postResponse = JsonRequest.post(
                CUSTOMER_API_URL + CUSTOMERS_PATH + FIND_OR_CREATE + requestFlag,
                JsonParser.classToJsonString(customer));

        when(" there is a request to retrieve customers data using DXP");
        DxpCustomerAssertions dxpAssertions = new DxpCustomerAssertions(
                DxpClient.getCustomer(ResponseUtils.extractCustomerNumber(postResponse)));

        then("  the latestPCWQuoteDate will be set to current date");
        var date = new DateTime().toLocalDate();
        dxpAssertions.assertMessageValueContentNull("latestPCWQuoteDate");

    }
}
