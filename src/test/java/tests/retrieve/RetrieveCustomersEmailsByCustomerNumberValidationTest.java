package tests.retrieve;

import api.requests.BodyFactory;
import assertions.CustomerAssertions;
import api.requests.help.JsonRequest;
import logging.Steps;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import testdata.CustomerDataHolder;
import testdata.StringRepository;
import static utils.EndpointsConfigurationData.CUSTOMERS_PATH;
import static utils.EndpointsConfigurationData.CUSTOMER_API_URL;
import static utils.EndpointsConfigurationData.EMAILS_SEARCH_BY_CUSTOMER_ID;

@Slf4j
public class RetrieveCustomersEmailsByCustomerNumberValidationTest {

    private BodyFactory bodyFactory = new BodyFactory();

    @BeforeEach
    public void init() {
        CustomerDataHolder.generateRandomDynamicTestData();
    }

    @Test
    public void tryToRetrieveEmailForNonExistingCustomer() {
        var customerId = "imNotExistingHopefully";

        Steps.when(" there is a request to retrieve details for non-existing customer");
        CustomerAssertions assertions = new CustomerAssertions(
            JsonRequest.get(CUSTOMER_API_URL + CUSTOMERS_PATH + "/" + customerId
                + EMAILS_SEARCH_BY_CUSTOMER_ID));

        Steps.then(" there will be empty array in response");
        assertions.assertResponseReceivedExpectedCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
            .assertErrorMessageValueContent(StringRepository.FAILED_GET_EMAIL);
    }

    @Test
    public void tryToRetrieveEmailForWildcardCustomer() {
        var customerId = "*";

        Steps.when(" there is a request to retrieve details for wildcard customerId");
        CustomerAssertions assertions = new CustomerAssertions(
            JsonRequest.get(CUSTOMER_API_URL + CUSTOMERS_PATH + "/" + customerId
                + EMAILS_SEARCH_BY_CUSTOMER_ID));

        Steps.then(" there will be empty array in response");
        assertions.assertResponseReceivedExpectedCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
            .assertErrorMessageValueContent(StringRepository.FAILED_GET_EMAIL);

    }

}
