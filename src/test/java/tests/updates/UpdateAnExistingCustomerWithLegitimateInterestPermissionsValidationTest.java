package tests.updates;

import api.model.LegitimateInterestBuilder;
import api.requests.CustomerClient;
import assertions.CustomerAssertions;
import logging.Steps;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parsing.json.JsonParser;
import steps.customer.helper.CustomerManager;
import testdata.CustomerDataHolder;
import utils.ResponseUtils;

import static utils.constants.CustomerBuilderData.generateRequiredCustomerData;

public class UpdateAnExistingCustomerWithLegitimateInterestPermissionsValidationTest {

    @BeforeEach
    void initTestData() {
        CustomerDataHolder.generateRandomDynamicTestData();
        generateRequiredCustomerData();
    }

    @Test
    public void modifyTheCustomerWithLegitimateInterestPermissionsWithMissingChannelValue() {

        Steps.given(" there is an existing customer");
        var customerId = ResponseUtils.extractCustomerNumber(
                CustomerManager.createFullCustomerWithAllFields()
        );

        Steps.when(
                " there is request to modify legitimate interest marketing permissions with missing channel value");
        var legitimateObject =
                new LegitimateInterestBuilder().create(true).withChannelValueSetTo("")
                        .build();
        CustomerAssertions assertions = new CustomerAssertions(
                CustomerClient.modifyCustomerLegitimateInterestWithPooling(customerId,
                        JsonParser.classToJsonString(legitimateObject)));

        Steps.then(" the request will be rejected with https 500 status code");
        assertions.assertResponseReceivedExpectedCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void modifyTheCustomerWithLegitimateInterestPermissionsWithMissingMarketValue() {

        Steps.given(" there is an existing customer");
        var customerId = ResponseUtils.extractCustomerNumber(
                CustomerManager.createFullCustomerWithAllFields()
        );

        Steps.when(
                " there is request to modify legitimate interest marketing permissions with missing channel value");
        var legitimateObject =
                new LegitimateInterestBuilder().create(true).setChannelsWithTheSameMarketValue(null)
                        .build();
        CustomerAssertions assertions = new CustomerAssertions(
                CustomerClient.modifyCustomerLegitimateInterestWithPooling(customerId,
                        JsonParser.classToJsonString(legitimateObject)));

        Steps.then(" the request will be rejected with https 500 status code");
        assertions.assertResponseReceivedExpectedCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void modifyTheCustomerWithLegitimateInterestPermissionsWithMissingSourceValue() {

        Steps.given(" there is an existing customer");
        var customerId = ResponseUtils.extractCustomerNumber(
                CustomerManager.createFullCustomerWithAllFields()
        );

        Steps.when(
                " there is request to modify legitimate interest marketing permissions with missing channel value");
        var legitimateObject =
                new LegitimateInterestBuilder().create(true).setChannelsWithTheSameSourceValue(null)
                        .build();
        CustomerAssertions assertions = new CustomerAssertions(
                CustomerClient.modifyCustomerLegitimateInterestWithPooling(customerId,
                        JsonParser.classToJsonString(legitimateObject)));

        Steps.then(" the request will be rejected with https 500 status code");
        assertions.assertResponseReceivedExpectedCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

}
