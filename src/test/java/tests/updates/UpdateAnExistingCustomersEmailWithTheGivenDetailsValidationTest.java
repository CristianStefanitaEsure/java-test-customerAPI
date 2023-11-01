package tests.updates;

import api.model.Customer;
import api.model.CustomerRequestBuilder;
import api.requests.BodyFactory;
import api.requests.CustomerClient;
import assertions.EmailEndpointAssertions;
import logging.Steps;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testdata.CustomerDataHolder;
import testdata.StringRepository;
import utils.ResponseUtils;
import utils.StringUtils;

import static utils.constants.CustomerBuilderData.generateRequiredCustomerData;

@Slf4j
public class UpdateAnExistingCustomersEmailWithTheGivenDetailsValidationTest {

    private BodyFactory bodyFactory = new BodyFactory();

    @BeforeEach
    public void init() {
        CustomerDataHolder.generateRandomDynamicTestData();
        generateRequiredCustomerData();
    }

    @Test
    public void updateCustomerEmailUsingIncorrectEmailId() {
        var customer = new CustomerRequestBuilder().createFullCustomerWithAllFields().build();

        Steps.given(" there is a customer with an existing email");
        Customer responseCustomer = ResponseUtils.parseResponseToCustomer(
                CustomerClient.createCustomer(customer));

        Steps.when(
                " there is a request to update customer email address with a new one, but the emailId is incorrect");
        var customerEmail = responseCustomer.getCustomerEmails().get(0);
        customerEmail.setId(StringUtils.generateRandomString(30));

        EmailEndpointAssertions emailEndpointAssertions = new EmailEndpointAssertions(
                CustomerClient.putCustomerEmailData(
                        responseCustomer.getCustomerNumber(), customerEmail));

        Steps.then(" the request will be rejected");
        emailEndpointAssertions.assertThatResponseReceivedExpectedCode(
                HttpStatus.SC_INTERNAL_SERVER_ERROR);
        emailEndpointAssertions.assertErrorMessageValueContent(
                StringRepository.INCORRECT_EMAIL_PUT_REQUEST);

    }

    @Test
    public void updateCustomerEmailUsingIncorrectEmail() {
        var customer = new CustomerRequestBuilder().createFullCustomerWithAllFields().build();

        Steps.given(" there is a customer with an existing email");
        Customer responseCustomer = ResponseUtils.parseResponseToCustomer(
                CustomerClient.createCustomer(customer));

        Steps.when(
                " there is a request to update customer email address with a new one, but the email is in incorrect format");
        var customerEmail = responseCustomer.getCustomerEmails().get(0);
        customerEmail.setEmail(StringUtils.generateRandomString(15));

        EmailEndpointAssertions emailEndpointAssertions = new EmailEndpointAssertions(
                CustomerClient.putCustomerEmailData(
                        responseCustomer.getCustomerNumber(), customerEmail));

        Steps.then(" the request will be rejected");
        emailEndpointAssertions.assertThatResponseReceivedExpectedCode(
                HttpStatus.SC_INTERNAL_SERVER_ERROR);
        emailEndpointAssertions.assertErrorMessageValueContent(
                StringRepository.INCORRECT_EMAIL_PUT_REQUEST);
    }

    @Test
    public void updateCustomerEmailUsingEmptyEmailField() {
        var customer = new CustomerRequestBuilder().createFullCustomerWithAllFields().build();

        Steps.given(" there is a customer with an existing email");
        Customer responseCustomer = ResponseUtils.parseResponseToCustomer(
                CustomerClient.createCustomer(customer));

        Steps.when(
                " there is a request to update customer email address with a new one, but the email address is missing");
        var customerEmail = responseCustomer.getCustomerEmails().get(0);
        customerEmail.setEmail("");

        EmailEndpointAssertions emailAssertions = new EmailEndpointAssertions(
                CustomerClient.putCustomerEmailData(
                        responseCustomer.getCustomerNumber(), customerEmail));

        Steps.then(" the request will be rejected");
        emailAssertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void updateCustomerEmailWithEmptyEmailConfirmedField() {
        var customer = new CustomerRequestBuilder().createFullCustomerWithAllFields().build();

        Steps.given(" there is a customer with an existing email");
        Customer responseCustomer = ResponseUtils.parseResponseToCustomer(
                CustomerClient.createCustomer(customer));

        Steps.when(
                " there is a request to update customer email address with a new one, but the email address is missing");
        var customerEmail = responseCustomer.getCustomerEmails().get(0);
        customerEmail.setEmailConfirmed(null);

        EmailEndpointAssertions emailEndpointAssertions = new EmailEndpointAssertions(
                CustomerClient.putCustomerEmailData(
                        responseCustomer.getCustomerNumber(), customerEmail));

        Steps.then(" the request will be rejected");
        emailEndpointAssertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_BAD_REQUEST);
    }
}

