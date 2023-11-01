package tests.updates;

import api.model.Customer;
import api.requests.BodyFactory;
import api.requests.CustomerClient;
import assertions.CustomerAssertions;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import logging.Steps;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testdata.CustomerDataHolder;
import utils.ResponseUtils;

import static utils.constants.CustomerBuilderData.generateRequiredCustomerData;

@Slf4j
public class UpdateAnExistingCustomerWithTheGivenDetailsTest {

    private BodyFactory bodyFactory = new BodyFactory();

    @BeforeEach
    public void init() {
        CustomerDataHolder.generateRandomDynamicTestData();
        generateRequiredCustomerData();
    }

    @Test
    public void ensureThatCustomerCreatedCanBeUpdated() {

    }

    @Test
    public void modifyCustomerData() {
        Steps.given("there is an existing customer in the system");
        HttpResponse<JsonNode> createResponse = CustomerClient.createCustomer(
                bodyFactory.fullCustomer());

        Steps.when("a user sends PUT request to modify the customer");
        Customer updatedCustomer = bodyFactory.fullModifiedCustomer(createResponse);
        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.updateAnExistingCustomerWithGivenDetails(
                        updatedCustomer));

        Steps.then("the customer data will be modified in the process");
        customerAssertions.assertResponseReceivedExpectedCode(HttpStatus.SC_OK)
                .assertCustomerRequestIsSimilarToResponse(updatedCustomer);
    }

    @Test
    public void successfulResponseBarsReturned() {
        Customer customerBody = bodyFactory.fullCustomer();
        Steps.given(" there is an existing customer created");
        Customer createCustomerResponse = ResponseUtils.parseResponseToCustomer(
                CustomerClient.createCustomer(customerBody));

        Steps.and(" there are bars associated with the Customer record");
        CustomerClient.updateAnExistingCustomerWithGivenDetails(
                bodyFactory.modifyCustomerWithBars(createCustomerResponse));

        Steps.when(" there is a request for Find/Create customer for the customer");

        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.findOrCreateWithPooling(createCustomerResponse));

        Steps.then(" response result will include customer bars associated with the customer");
        customerAssertions.assertBarsDataIsCorrect("json/bar.json");
    }

    @Test
    public void successfulResponseRemovedBarsReturned() {
        Customer customerBody = bodyFactory.fullCustomer();
        Steps.given(" there is an existing customer created");
        Customer createCustomerResponse = ResponseUtils.parseResponseToCustomer(
                CustomerClient.createCustomer(customerBody));

        Steps.and(" there are bars associated with the Customer record");
        Customer barsAddedResponse = ResponseUtils.parseResponseToCustomer(
                CustomerClient.updateAnExistingCustomerWithGivenDetails(
                        bodyFactory.modifyCustomerWithBars(createCustomerResponse)));

        Steps.and(" the bar is removed");
        CustomerClient.updateAnExistingCustomerWithGivenDetails(
                bodyFactory.modifyCustomerWithBars(barsAddedResponse));

        Steps.when(" there is a request for Find/Create customer for the customer");

        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.findOrCreateWithPooling(
                        bodyFactory.modifyCustomerWithRemoveBars(barsAddedResponse)));

        Steps.then(" response result will include customer bars associated with the customer");
        customerAssertions.assertBarsDataIsCorrect("json/barRemoved.json");
    }

}
