package tests.updates;

import api.model.Customer;
import api.model.CustomerBarsBuilder;
import api.model.CustomerRequestBuilder;
import api.model.CustomerSearchRequestBuilder;
import api.model.definedvalues.BarReason;
import api.requests.CustomerClient;
import api.requests.help.JsonRequest;
import assertions.CustomerAssertions;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import parsing.json.JsonParser;
import testdata.CustomerDataHolder;
import utils.ResponseUtils;

import static logging.Steps.*;
import static utils.EndpointsConfigurationData.*;
import static utils.constants.CustomerBuilderData.generateRequiredCustomerData;

public class UpdateAnExistingCustomerWithBarsValidationTests {

    @BeforeEach
    public void init() {
        CustomerDataHolder.generateRandomDynamicTestData();
        generateRequiredCustomerData();
    }

    @Test
    public void nullBarTypeAttribute() {
        var customerBars = new CustomerBarsBuilder().createUWFullBar().build();
        customerBars.get(0).setBarType(null);
        var customer = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();

        given(" there is an existing customer");
        var customerFromResponse = ResponseUtils.parseResponseToCustomer(
                CustomerClient.createCustomer(customer));

        and(" customer is available for customer search");
        var searchRequest = new CustomerSearchRequestBuilder().create()
                .setSearchFromCustomerObject(customerFromResponse).build();
        CustomerClient.searchCustomerUntilSuccessOrTimeout(searchRequest);

        when(" there is a request to update customer bars with null value for the bar type");
        var assertions = new CustomerAssertions(
                JsonRequest.put(
                        CUSTOMER_API_URL + CUSTOMERS_PATH + "/"
                                + customerFromResponse.getCustomerNumber() + CUSTOMER_BARS,
                        JsonParser.classToJsonString(customerBars)));

        then(" the request will be rejected as bad request");
        assertions.assertResponseReceivedExpectedCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .assertErrorMessageValueContent("Bar Type must be supplied");
    }

    @Test
    public void incorrectValueForBarTypeAttribute() {
        var customerBars = new CustomerBarsBuilder().createUWFullBar().build();
        customerBars.get(0).setBarType("");
        var customer = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();

        given(" there is an existing customer");
        var customerFromResponse = ResponseUtils.parseResponseToCustomer(
                CustomerClient.createCustomer(customer));

        and(" customer is available for customer search");
        var searchRequest = new CustomerSearchRequestBuilder().create()
                .setSearchFromCustomerObject(customerFromResponse).build();
        CustomerClient.searchCustomerUntilSuccessOrTimeout(searchRequest);

        when(" there is a request to update customer bars with incorrect value for the bar type");
        var assertions = new CustomerAssertions(
                JsonRequest.put(
                        CUSTOMER_API_URL + CUSTOMERS_PATH + "/"
                                + customerFromResponse.getCustomerNumber() + CUSTOMER_BARS,
                        JsonParser.classToJsonString(customerBars)));

        then(" the request will be rejected as bad request");
        assertions.assertResponseReceivedExpectedCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .assertErrorMessageValueContent("BarType lookup code must be one of these values");
    }

    @Test
    public void addUWBarToACustomerWithNullCategory() {
        Customer customer = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();

        given(" there is an existing customer");
        var customerFromResponse = ResponseUtils.parseResponseToCustomer(
                CustomerClient.createCustomer(customer));

        and(" customer is available for customer search");
        var searchRequest = new CustomerSearchRequestBuilder().create()
                .setSearchFromCustomerObject(customerFromResponse).build();
        CustomerClient.searchCustomerUntilSuccessOrTimeout(searchRequest);

        when(" there is a request to update customer bars with null value for the category");
        var customerBars = new CustomerBarsBuilder().createUWFullBar().build();
        customerBars.get(0).setCategory(null);

        var assertions = new CustomerAssertions(
                JsonRequest.put(
                        CUSTOMER_API_URL + CUSTOMERS_PATH + "/"
                                + customerFromResponse.getCustomerNumber() + CUSTOMER_BARS,
                        JsonParser.classToJsonString(customerBars)));

        then(" the request will be rejected as bad request");
        assertions.assertResponseReceivedExpectedCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .assertErrorMessageValueContent("Category must be supplied");
    }

    @Test
    public void addUWBarToACustomerWithNullReason() {
        Customer customer = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();

        given(" there is an existing customer");
        var customerFromResponse = ResponseUtils.parseResponseToCustomer(
                CustomerClient.createCustomer(customer));

        and(" customer is available for customer search");
        var searchRequest = new CustomerSearchRequestBuilder().create()
                .setSearchFromCustomerObject(customerFromResponse).build();
        CustomerClient.searchCustomerUntilSuccessOrTimeout(searchRequest);


        when(" there is a request to update customer bars with null value for the reason");
        var customerBars = new CustomerBarsBuilder().createUWFullBar().build();
        customerBars.get(0).setReason(null);

        var assertions = new CustomerAssertions(
                JsonRequest.put(
                        CUSTOMER_API_URL + CUSTOMERS_PATH + "/"
                                + customerFromResponse.getCustomerNumber() + CUSTOMER_BARS,
                        JsonParser.classToJsonString(customerBars)));

        then(" the request will be rejected as bad request");
        assertions.assertResponseReceivedExpectedCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .assertErrorMessageValueContent("Reason must be supplied");
    }

    @ParameterizedTest
    @ValueSource(strings = {"ACC_FLG", "ACC_BAR", "VAL_INV", "FRD_BAR"})
    public void addNonUWBarToACustomerWithReasonAndCategory(String barType) {

        Customer customer = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();

        given(" there is an existing customer");
        var customerFromResponse = ResponseUtils.parseResponseToCustomer(
                CustomerClient.createCustomer(customer));


        when(" there is request to add bar to a customer");
        var customerBars = new CustomerBarsBuilder().createNonUWBarsWithValue(barType).build();
        customerBars.get(0).setCategory(BarReason.OTHER.getCategory().toString());
        customerBars.get(0).setReason(BarReason.OTHER.toString());

        and(" customer is available for customer search");
        var searchRequest = new CustomerSearchRequestBuilder().create()
                .setSearchFromCustomerObject(customerFromResponse).build();
        CustomerClient.searchCustomerUntilSuccessOrTimeout(searchRequest);

        when(" there is a request to update non UW customer bars with category and reason");
        var assertions = new CustomerAssertions(
                JsonRequest.put(
                        CUSTOMER_API_URL + CUSTOMERS_PATH + "/"
                                + customerFromResponse.getCustomerNumber() + CUSTOMER_BARS,
                        JsonParser.classToJsonString(customerBars)));

        then(" the request will be rejected as bad request");
        assertions.assertResponseReceivedExpectedCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .assertErrorMessageValueContent("Failed to update customer");
        //TODO : update the message once we have proper message from dxp side
    }

    @ParameterizedTest
    @ValueSource(strings = {"ACC_FLG", "ACC_BAR", "VAL_INV", "FRD_BAR"})
    public void addNonUWBarToACustomerWithReason(String barType) {

        Customer customer = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();

        given(" there is an existing customer");
        var customerFromResponse = ResponseUtils.parseResponseToCustomer(
                CustomerClient.createCustomer(customer));


        when(" there is request to add bar to a customer");
        var customerBars = new CustomerBarsBuilder().createNonUWBarsWithValue(barType).build();
        customerBars.get(0).setReason(BarReason.OTHER.toString());

        and(" customer is available for customer search");
        var searchRequest = new CustomerSearchRequestBuilder().create()
                .setSearchFromCustomerObject(customerFromResponse).build();
        CustomerClient.searchCustomerUntilSuccessOrTimeout(searchRequest);

        when(" there is a request to update non UW customer bars with reason");
        var assertions = new CustomerAssertions(
                JsonRequest.put(
                        CUSTOMER_API_URL + CUSTOMERS_PATH + "/"
                                + customerFromResponse.getCustomerNumber() + CUSTOMER_BARS,
                        JsonParser.classToJsonString(customerBars)));

        then(" the request will be rejected as bad request");
        assertions.assertResponseReceivedExpectedCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .assertErrorMessageValueContent("Failed to update customer");
    }

    @ParameterizedTest
    @ValueSource(strings = {"ACC_FLG", "ACC_BAR", "VAL_INV", "FRD_BAR"})
    public void addNonUWBarToACustomerWithCategory(String barType) {

        Customer customer = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();

        given(" there is an existing customer");
        var customerFromResponse = ResponseUtils.parseResponseToCustomer(
                CustomerClient.createCustomer(customer));


        when(" there is request to add bar to a customer");
        var customerBars = new CustomerBarsBuilder().createNonUWBarsWithValue(barType).build();
        customerBars.get(0).setCategory(BarReason.OTHER.getCategory().toString());

        and(" customer is available for customer search");
        var searchRequest = new CustomerSearchRequestBuilder().create()
                .setSearchFromCustomerObject(customerFromResponse).build();
        CustomerClient.searchCustomerUntilSuccessOrTimeout(searchRequest);

        when(" there is a request to update non UW customer bars with category and reason");
        var assertions = new CustomerAssertions(
                JsonRequest.put(
                        CUSTOMER_API_URL + CUSTOMERS_PATH + "/"
                                + customerFromResponse.getCustomerNumber() + CUSTOMER_BARS,
                        JsonParser.classToJsonString(customerBars)));

        then(" the request will be rejected as bad request");
        assertions.assertResponseReceivedExpectedCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .assertErrorMessageValueContent("Failed to update customer");
    }

}
