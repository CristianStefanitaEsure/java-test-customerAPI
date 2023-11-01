package tests.retrieve;

import api.requests.BodyFactory;
import api.requests.CustomerClient;
import assertions.CustomerAssertions;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testdata.CustomerDataHolder;
import utils.ResponseUtils;
import utils.StringUtils;

import static logging.Steps.*;
import static utils.constants.CustomerBuilderData.generateRequiredCustomerData;

@Slf4j
public class RetrieveCustomerByEmailTest {

    private BodyFactory bodyFactory = new BodyFactory();

    @BeforeEach
    public void init() {
        CustomerDataHolder.generateRandomDynamicTestData();
        generateRequiredCustomerData();
    }

    /* Happy Tests - naming convention should be reviewed
     * */
    @Test
    public void searchForCustomerUsingExactSameEmailAddress() {
        String emailAddress = StringUtils.generateRandomEmail();

        given("there is an existing customer");
        String customerId = ResponseUtils
                .extractCustomerNumber(
                        CustomerClient.createCustomer(bodyFactory.customerWithModifiedEmail(emailAddress)));

        when(" a user sends request to retrieve a customer with existing email");
        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.getCustomerByEmail(emailAddress));

        then(" customerId will be present");
        customerAssertions.assertResponseResultsContainString(customerId);
    }

    @Test
    public void verifyIfSearchFunctionalityIsCaseSensitive() {
        String emailAddress = StringUtils.generateRandomEmail();

        given("there is an existing customer");
        String customerId = ResponseUtils
                .extractCustomerNumber(
                        CustomerClient.createCustomer(bodyFactory.customerWithModifiedEmail(emailAddress)));

        when(" a user sends request to retrieve a customer with existing email but all letters capital");
        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.getCustomerByEmail(emailAddress.toUpperCase()));

        then(" customerId will be present in the results");
        customerAssertions.assertResponseResultsContainString(customerId);
    }

}
