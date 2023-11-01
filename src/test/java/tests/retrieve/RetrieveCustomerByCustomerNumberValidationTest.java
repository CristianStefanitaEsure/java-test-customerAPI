package tests.retrieve;

import api.requests.BodyFactory;
import api.requests.CustomerClient;
import assertions.CustomerAssertions;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testdata.CustomerDataHolder;

import static logging.Steps.given;
import static logging.Steps.then;
import static testdata.StringRepository.FAILED_GET_CUSTOMER;
import static testdata.StringRepository.INCORRECT_CUSTOMER_ID;

@Slf4j
public class RetrieveCustomerByCustomerNumberValidationTest {

    private BodyFactory bodyFactory = new BodyFactory();

    @BeforeEach
    public void init() {
        CustomerDataHolder.generateRandomDynamicTestData();
    }

    @Test
    public void nonExistingCustomerId() {
        given(" a user sends a request to GET with non existing customer");
        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.getCustomerByCustomerNumber
                        (INCORRECT_CUSTOMER_ID));

        then(" a user should receive negative response");
        customerAssertions.assertErrorMessageValueContent(FAILED_GET_CUSTOMER);
    }

    @Test
    public void sendWildmarkAsParameter() {
        given(" a user sends a request to GET with wildcard as a customer number");
        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.getCustomerByCustomerNumber("*"));

        then(" a user should receive negative response");
        customerAssertions.assertErrorMessageValueContent(FAILED_GET_CUSTOMER);
    }

}
