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
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import parsing.json.JsonParser;
import utils.ResponseUtils;

import static logging.Steps.*;
import static org.junit.jupiter.api.Assertions.*;
import static steps.customer.helper.CustomerManager.createFullCustomerWithAllFields;
import static utils.EndpointsConfigurationData.*;
import static utils.constants.CustomerBuilderData.generateRequiredCustomerData;

@Slf4j
public class FindOrCreateCustomerTest {

    private BodyFactory bodyFactory = new BodyFactory();

    @BeforeEach
    public void init() {
        generateRequiredCustomerData();
    }

    @Test
    public void ensureCustomerIsNotCreatedOnFindAndCreateEndpointBecauseItAlreadyExist() {
        given("a request is sent to create a Customer via the customer endpoint: ");
        var customerResponse = createFullCustomerWithAllFields();
        String customerId = ResponseUtils.extractCustomerNumber(customerResponse);

        when("a user sends the same request to create customer via findOrCreate endpoint");
        var requestBody = ResponseUtils.parseResponseToCustomer(customerResponse);
        var findOrCreateResponse = CustomerClient.findOrCreateWithPooling(requestBody);

        String findOrCreateCustomerId = ResponseUtils.extractCustomerNumber(findOrCreateResponse);

        then("customer should not be created and same customer Id should be returned");
        new CustomerAssertions(customerResponse).assertResponseReceivedExpectedCode(
                HttpStatus.SC_CREATED);
        new CustomerAssertions(findOrCreateResponse).assertResponseReceivedExpectedCode(
                HttpStatus.SC_OK);

        assertEquals(customerId, findOrCreateCustomerId, "customer Id should match");

        and("a request sent to DXP will return user details");
        new CustomerAssertions(
                DxpClient.getCustomer(customerId)).assertResponseReceivedExpectedCode(HttpStatus.SC_OK);
    }

    /* Happy Tests - naming convention should be reviewed
     * */
    @Disabled("20 seconds not enough - please revisit")
    @Test
    public void successfulResponseNoBarsReturned() {
        Customer customerBody = bodyFactory.fullCustomer();

        given(" there is an existing customer created");
        CustomerClient.createCustomer(
                customerBody);

        and(" there are NO bars associated with the Customer record");
        assertNull(customerBody.getCustomerBars());

        when(" there is a request for Find/Create customer for the customer");
        Customer customerResponse = ResponseUtils.parseResponseToCustomer(
                CustomerClient.findOrCreateWithPooling(customerBody));

        then(" response result will include empty customer bars associated with the customer");
        assertTrue(customerResponse.getCustomerBars().isEmpty());
    }

    @Test
    public void createCustomerWithNoFlagPassed() {
        Customer customer = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();

        given(" there is a request to create customer using find-or-create endpoint with no flag");
        var customerId = ResponseUtils.extractCustomerNumber(
                CustomerClient.findOrCreateWithPooling(customer));

        when(" there is a request to retrieve customers data using DXP");
        DxpCustomerAssertions dxpAssertions = new DxpCustomerAssertions(DxpClient.getCustomer(customerId));

        then(" latestPCWQuoteDate will be null");
        dxpAssertions.assertValueIsPresentInObject("latestPCWQuoteDate", null);
    }

    @Test
    public void createCustomerWithFlagTrue() {
        var requestFlag = "?isPCWQuoteRequest=true";
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

        dxpAssertions.assertValueIsPresentInObject("latestPCWQuoteDate", date.toString());
    }

    @Test
    public void modifyFlagForAnExistingCustomer() {
        var requestFlag = "?isPCWQuoteRequest=true";
        Customer customer = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();

        given(" there is an existing customer");
        var customerId = ResponseUtils.extractCustomerNumber(
                CustomerClient.findOrCreateWithPooling(customer));

        and(" the customer has PCWQuoteRequest flag set to null");
        DxpCustomerAssertions dxpAssertions = new DxpCustomerAssertions(DxpClient.getCustomer(customerId));
        dxpAssertions.assertValueIsPresentInObject("latestPCWQuoteDate", null);

        when(
                " there is a request to find-or-create endpoint with the PCWQuoteRequest flag set to true");
        HttpResponse<JsonNode> response2 = CustomerClient.postWithPooling(
                CUSTOMER_API_URL + CUSTOMERS_PATH + FIND_OR_CREATE + requestFlag,
                customer);

        then(" the customer will have latestPCWQuoteDate set to date of request");
        var date = new DateTime().toLocalDate();

        DxpCustomerAssertions updateAssertions = new DxpCustomerAssertions(DxpClient.getCustomer(customerId));
        updateAssertions.assertValueIsPresentInObject("latestPCWQuoteDate", date.toString());
    }

}
