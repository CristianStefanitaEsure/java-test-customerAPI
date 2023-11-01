package tests.create;

import api.model.Customer;
import api.requests.BodyFactory;
import api.requests.CustomerClient;
import api.requests.DxpClient;
import assertions.CompareCustomers;
import assertions.CustomerAssertions;
import assertions.DxpCustomerAssertions;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testdata.CustomerDataHolder;
import utils.ResponseUtils;

import static logging.Steps.*;
import static steps.customer.helper.CustomerManager.createCustomerWithMinimumFields;
import static steps.customer.helper.CustomerManager.getCustomerRequest;
import static utils.constants.CustomerBuilderData.generateRequiredCustomerData;

@Slf4j
public class CreateCustomerTest {

    private BodyFactory bodyFactory = new BodyFactory();

    @BeforeEach
    public void init() {
        CustomerDataHolder.generateRandomDynamicTestData();
        generateRequiredCustomerData();
    }

    @Test
    public void ensureCustomerIsCreatedWithMinimumFields() {
        given("a request is sent to create a customer with minimum fields: ");
        var customerResponse = createCustomerWithMinimumFields();

        then("success response should be received");
        new CustomerAssertions(customerResponse).assertResponseReceivedExpectedCode(
                HttpStatus.SC_CREATED);
        String customerId = ResponseUtils.extractCustomerNumber(customerResponse);
        CompareCustomers.assertMinimalCustomerResponseIsTheSame(getCustomerRequest(),
                ResponseUtils.parseResponseToCustomer(customerResponse));

        and("a request sent to DXP will return user details");
        DxpCustomerAssertions assertions = new DxpCustomerAssertions(DxpClient.getCustomer(customerId));
        assertions.compareMandatoryFieldsCustomerData(getCustomerRequest());
    }

    @Test
    public void ensureCustomerIsCreatedWithAllFields() {
        Customer customer = bodyFactory.fullCustomer();
        when("a user sends a request to create customer with all data provided");
        HttpResponse<JsonNode> requestResponse = CustomerClient.createCustomer(
                customer);
        var customerResponse = ResponseUtils.parseResponseToCustomer(requestResponse);

        then("success requestResponse should be received");
        new CustomerAssertions(requestResponse).assertResponseReceivedExpectedCode(
                HttpStatus.SC_CREATED);
        CompareCustomers.assertCustomerResponseIsTheSame(customerResponse,
                ResponseUtils.parseResponseToCustomer(requestResponse));

        and("a request sent to DXP will return user details");
        DxpCustomerAssertions dxpAssertions = new DxpCustomerAssertions(
                DxpClient.getCustomer(customerResponse.getCustomerNumber()));
        dxpAssertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
        dxpAssertions.compareMandatoryFieldsCustomerData(customerResponse)
                .compareEmployment(customerResponse.getCustomerEmployments())
                .compareEmails(customerResponse.getCustomerEmails())
                .compareLegitimateInterest(customerResponse.getCustomerLegitimateInterestMarketingPermissions())
                .comparePhone(customerResponse.getCustomerPhones());
    }
}