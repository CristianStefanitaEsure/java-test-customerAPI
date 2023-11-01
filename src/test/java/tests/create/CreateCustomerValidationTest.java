package tests.create;

import api.requests.BodyFactory;
import api.requests.CustomerClient;
import api.requests.DxpClient;
import assertions.CustomerAssertions;
import assertions.LegitimateInterestAssertions;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testdata.CustomerDataHolder;
import testdata.StringRepository;
import utils.ResponseUtils;
import utils.StringUtils;

import static logging.Steps.then;
import static logging.Steps.when;

@Slf4j
public class CreateCustomerValidationTest {

    private final BodyFactory bodyFactory = new BodyFactory();

    @BeforeEach
    public void init() {
        CustomerDataHolder.generateRandomDynamicTestData();
    }

    @Test
    public void testEmptyValuesForCreateCustomerMandatoryAttributes() {
        when("a user sends POST request to create a customer with empty values for mandatory attribute");
        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.createCustomer(bodyFactory.emptyCustomerAPIValidatedFields()));

        then("a user will receive HTTP 400 response");
        customerAssertions.assertResponseReceivedExpectedCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testNullValuesForCreateCustomerMandatoryAttributes() {
        when(
                "a user sends POST request to create a customer with null values for mandatory attribute");
        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.createCustomer(bodyFactory.nullValuesForCustomerAPIValidatedFields()));

        then("a user will receive HTTP 400 response");
        customerAssertions.assertResponseReceivedExpectedCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void validateIfCustomerIsRejectedWithMissingBrandsValue() {
        when(" user sends a request with no brands inside");
        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.createCustomer(bodyFactory.missingBrands()));

        then(" user will receive bad request response");
        customerAssertions.assertResponseReceivedExpectedCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void validateIfCustomerWithNoHouseNumerAndHouseNameIsRejected() {
        when(" user sends a request with no house name and house number");
        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.createCustomer(bodyFactory.customerWithEmptyHouseNameHouseNumber()));
        then(" user will receive bad request response");
        customerAssertions.assertResponseReceivedExpectedCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void validateMaximumLengthForFirstAndLastNameFields() {
        when(" user sends a request with too long first and last name");
        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient
                        .createCustomer(bodyFactory.customerWithCustomFirstAndLastName(
                                StringUtils.generateRandomString(60), StringUtils.generateRandomString(60))));

        then(" user will receive bad request response");
        customerAssertions.assertResponseReceivedExpectedCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        customerAssertions.assertErrorMessageValueContent(StringRepository.TOO_LONG_VALUE);
    }

    @Test
    public void validateIfBrandsIsDefaultedToProperValue() {
        when("a user sends request to create a customer with proper brand");
        HttpResponse<JsonNode> response = CustomerClient.createCustomer(
                bodyFactory.minimumFieldsCustomer());

        then("a request to DXP to retrieve information will include customer brand");
        CustomerAssertions customerAssertions = new CustomerAssertions(
                DxpClient.getCustomer(ResponseUtils.extractCustomerNumber(response)));
        customerAssertions.assertResponseReceivedExpectedCode(HttpStatus.SC_OK).
                assertResponseResultsContainString("ES");
    }

    @Test
    public void validateIfLegitimateInterestIsDefaultedToProperValue() {
        when("a user sends request to create a customer with proper brand");
        HttpResponse<JsonNode> response = CustomerClient.createCustomer(
                bodyFactory.minimumFieldsCustomer());

        then("a request to DXP to retrieve information will include legitimate interest details");
        LegitimateInterestAssertions assertions = new LegitimateInterestAssertions(
                CustomerClient.getCustomerByCustomerNumber(
                        ResponseUtils.extractCustomerNumber(response)));
        assertions.extractPermissionsMap().marketingPreferencesExistingStructure();
    }
}
