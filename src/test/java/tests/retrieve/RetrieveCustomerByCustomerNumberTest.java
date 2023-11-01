package tests.retrieve;

import api.model.Customer;
import api.requests.BodyFactory;
import api.requests.CustomerClient;
import api.requests.DxpClient;
import assertions.CustomerAssertions;
import assertions.LegitimateInterestAssertions;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import logging.Steps;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import steps.customer.helper.CustomerManager;
import testdata.CustomerDataHolder;
import utils.EnvironmentsNew;
import utils.ResponseUtils;

import static logging.Steps.*;
import static steps.customer.helper.CustomerManager.createCustomerWithMinimumFields;
import static utils.constants.CustomerBuilderData.generateRequiredCustomerData;

@Slf4j
public class RetrieveCustomerByCustomerNumberTest {

    private final BodyFactory bodyFactory = new BodyFactory();

    @BeforeAll
    static void initAll() {
        EnvironmentsNew.setup("app06");

        CustomerDataHolder.generateRandomDynamicTestData();
        generateRequiredCustomerData();
    }

    @Disabled
    @Test
    public void ensureCustomerIsCreatedWithAllMandatoryFields() {
        given("a request is sent to create a customer: ");
        var customerResponse = createCustomerWithMinimumFields();
        log.info("Customer Created: " + ResponseUtils.printer(customerResponse.getBody(),
                CustomerClient.mapper));

        String customerId = ResponseUtils.extractCustomerNumber(customerResponse);

        then("success requestResponse should be received");
        CustomerAssertions createAssertion = new CustomerAssertions(customerResponse);
        createAssertion.assertThatResponseReceivedExpectedCode(HttpStatus.SC_CREATED);

        and("a request sent to DXP will return user details");
        CustomerAssertions dxpResponse = new CustomerAssertions(DxpClient.getCustomer(customerId));
        dxpResponse.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
    }

    @Test
    public void getCustomerByCustomerNumberWithProperCustomerId() {
        given("there is an existing customer in the system");
        HttpResponse<JsonNode> createResponse = CustomerClient.createCustomer(
                bodyFactory.minimumFieldsCustomer());
        log.info("Customer Created: " + ResponseUtils.printer(createResponse.getBody(),
                CustomerClient.mapper));

        String customerId = ResponseUtils.extractCustomerNumber(createResponse);

        when("a user sends GET request to retrieve customer details");
        CustomerAssertions assertions = new CustomerAssertions(
                CustomerClient.getCustomerByCustomerNumber(customerId));

        then("proper response is received by a user");
        assertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
    }
    /* Happy Tests - naming convention should be reviewed
     * */

    @Test
    public void testVerifyEmailsAndPhonesAndConfirmationsForNewCustomer() {
        given("there is an existing customer in the system");
        Customer createResponseCustomer = ResponseUtils.parseResponseToCustomer(
                CustomerClient.createCustomer(
                        bodyFactory.fullCustomer()));

        String customerId = createResponseCustomer.getCustomerNumber();

        when("a user sends GET request to retrieve customer details");
        CustomerAssertions assertions = new CustomerAssertions(
                CustomerClient.getCustomerByCustomerNumber(customerId));

        then("proper response is received by a user");
        assertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);

        assertions
                .assertThatResponseHasEmailsDataInResults(createResponseCustomer.getCustomerEmails());

        assertions
                .assertThatResponseHasPhonesDataInResults(createResponseCustomer.getCustomerPhones());
    }

    @Test
    public void verifyLegitimateInterestIsPresentForGetCustomer() {
        Steps.given(" there is a request to create a new customer");
        HttpResponse<JsonNode> response = CustomerManager.createCustomerWithMinimumFields();
        var customerId = ResponseUtils.extractCustomerNumber(response);

        Steps.when(" there is a request to retrieve this customers data");
        LegitimateInterestAssertions getAssertion = new LegitimateInterestAssertions(CustomerClient.getCustomerByCustomerNumber(customerId));
        getAssertion.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);

        Steps.then(" the LI data will be visible");
        getAssertion.extractPermissionsMap().marketingPreferencesExistingStructure();
    }
}
