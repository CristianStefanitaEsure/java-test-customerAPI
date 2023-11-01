package tests;

import api.model.CustomerBarsBuilder;
import api.model.CustomerRequestBuilder;
import api.model.LegitimateInterestBuilder;
import api.requests.BodyFactory;
import api.requests.CustomerClient;
import assertions.CustomerAssertions;
import assertions.CustomerBarsAssertions;
import assertions.EmailEndpointAssertions;
import assertions.LegitimateInterestAssertions;
import com.github.javafaker.Faker;
import logging.Steps;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parsing.json.JsonParser;
import testdata.CustomerDataHolder;
import utils.ResponseUtils;
import utils.StringUtils;

import static utils.constants.CustomerBuilderData.generateRequiredCustomerData;

@Slf4j
public class EndToEndCustomerTest {

    private final BodyFactory bodyFactory = new BodyFactory();

    @BeforeEach
    public void init() {
        CustomerDataHolder.generateRandomDynamicTestData();
        generateRequiredCustomerData();
    }

    @Test
    public void verifyCustomerInteractionsWithAllEndpoints() {
        var customerRequest = new CustomerRequestBuilder().createFullCustomerWithAllFields()
                .build();

        Steps.given(" there is a request to create a customer");
        var customerJsonResponse = CustomerClient.findOrCreate(customerRequest);
        var customerResponseObject = ResponseUtils.parseResponseToCustomer(customerJsonResponse);

        Steps.then("the customer will be create with proper content");
        var customerAssertions = new CustomerAssertions(customerJsonResponse);
        customerAssertions.assertCustomerRequestIsSimilarToResponse(customerRequest);

        Steps.and("it is possible to update customers email");
        var customerEmail = customerResponseObject.getCustomerEmails().get(0);
        customerEmail.setEmail(StringUtils.generateRandomEmail());
        CustomerClient.putCustomerEmailData(customerResponseObject.getCustomerNumber(),
                customerEmail);
        var emailUpdateAssertions = new EmailEndpointAssertions(
                CustomerClient.getEmailByCustomerNumber(
                        customerResponseObject.getCustomerNumber()));
        emailUpdateAssertions.assertEmailIsTheSameAsInRequest(customerEmail);

        Steps.and("it is possible to update LI for the customer");
        var updateLiRequest = new LegitimateInterestBuilder().create(true).setEmail(false).build();
        CustomerClient.modifyCustomerLegitimateInterestWithPooling(
                customerResponseObject.getCustomerNumber(),
                JsonParser.classToJsonString(updateLiRequest));
        var liUpdateAssertions = new LegitimateInterestAssertions(
                CustomerClient.getCustomerByCustomerNumber(
                        customerResponseObject.getCustomerNumber()));
        liUpdateAssertions.extractPermissionsMap().compareValuesWithRequest(updateLiRequest);

        Steps.and("it is possible to add a customer bar");
        var barRequest = new CustomerBarsBuilder().createUWFullBar().build();
        CustomerClient.sendPutBarsRequest(customerResponseObject.getCustomerNumber(), barRequest);
        var barAssertions = new CustomerBarsAssertions(CustomerClient.getCustomerByCustomerNumber(
                customerResponseObject.getCustomerNumber()));
        barAssertions.assertBarsDataIsCorrect(barRequest.get(0));

        Steps.and(
                "it is possible to retrieve different information for remaining get endpoints");
        var emailEndpointAssertions = new EmailEndpointAssertions(
                CustomerClient.getCustomerByEmail(customerEmail.getEmail()));
        emailEndpointAssertions.assertThatResponseHasDataInResults(
                customerResponseObject.getCustomerNumber());
    }
}
