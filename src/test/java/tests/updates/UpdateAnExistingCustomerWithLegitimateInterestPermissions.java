package tests.updates;

import api.model.Customer;
import api.model.LegitimateInterestBuilder;
import api.requests.CustomerClient;
import assertions.LegitimateInterestAssertions;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import logging.Steps;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parsing.json.JsonParser;
import steps.customer.helper.CustomerManager;
import testdata.CustomerDataHolder;
import utils.ResponseUtils;

import static utils.constants.CustomerBuilderData.generateRequiredCustomerData;

@Slf4j
public class UpdateAnExistingCustomerWithLegitimateInterestPermissions {

    @BeforeEach
    void initTestData() {
        CustomerDataHolder.generateRandomDynamicTestData();
        generateRequiredCustomerData();
    }

    @Test
    public void addLegitimateInterestMarketingPermissions() {
        Customer customerRecord;

        Steps.given(" there is an existing customer");
        customerRecord = ResponseUtils.parseResponseToCustomer(
                CustomerManager.createCustomerWithMinimumFields());
        var customerId = customerRecord.getCustomerNumber();

        Steps.when(
                " there is a request to update customer with legitimate interest marketing permissions");
        var customerLegitimateInterest =
                new LegitimateInterestBuilder().create(true).build();

        HttpResponse<JsonNode> response = CustomerClient.modifyCustomerLegitimateInterestWithPooling(
                customerId,
                JsonParser.classToJsonString(customerLegitimateInterest));

        Steps.then(" response will include values set for the legitimate interest");
        LegitimateInterestAssertions interestAssertions = new LegitimateInterestAssertions(
                CustomerClient.getCustomerByCustomerNumber(customerId));
        interestAssertions.extractPermissionsMap()
                .compareValuesWithRequest(customerLegitimateInterest);

    }

    @Test
    public void modifyLegitimateInterestMarketingPermissions() {
        Customer customerRecord;
        var customerLegitimateInterest =
                new LegitimateInterestBuilder().create(true).build();

        Steps.given(
                " there is an existing customer with letigitmate interest marketing permissions");
        customerRecord = ResponseUtils.parseResponseToCustomer(
                CustomerManager.createCustomerWithMinimumFields());
        var customerId = customerRecord.getCustomerNumber();

        CustomerClient.modifyCustomerLegitimateInterestWithPooling(
                customerId,
                JsonParser.classToJsonString(customerLegitimateInterest));

        Steps.and(" there is another request to modify legitimate interest marketing permissions");
        var newCustomerLegitimateInterest = new LegitimateInterestBuilder().create(
                false).build();

        CustomerClient.modifyCustomerLegitimateInterestWithPooling(
                customerId,
                JsonParser.classToJsonString(newCustomerLegitimateInterest));

        Steps.then(" response will include values set for the legitimate interest");
        LegitimateInterestAssertions interestAssertions = new LegitimateInterestAssertions(
                CustomerClient.getCustomerByCustomerNumber(customerId));
        interestAssertions.extractPermissionsMap()
                .compareValuesWithRequest(newCustomerLegitimateInterest);
    }
}
