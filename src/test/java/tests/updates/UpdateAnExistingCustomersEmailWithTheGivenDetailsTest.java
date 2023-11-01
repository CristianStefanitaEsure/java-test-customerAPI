package tests.updates;

import api.model.Customer;
import api.model.CustomerRequestBuilder;
import api.requests.CustomerClient;
import assertions.EmailEndpointAssertions;
import com.github.javafaker.Faker;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import logging.Steps;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testdata.CustomerDataHolder;
import utils.ResponseUtils;
import utils.StringUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static utils.constants.CustomerBuilderData.generateRequiredCustomerData;

@Slf4j
public class UpdateAnExistingCustomersEmailWithTheGivenDetailsTest {

    @BeforeEach
    public void init() {
        CustomerDataHolder.generateRandomDynamicTestData();
        generateRequiredCustomerData();
    }

    @Test
    public void sendRequestToUpdateCustomerEmail() {
        var customer = new CustomerRequestBuilder().createFullCustomerWithAllFields().build();

        Steps.given(" there is a customer with an existing email");
        Customer responseCustomer = ResponseUtils.parseResponseToCustomer(
                CustomerClient.createCustomer(customer));

        Steps.when(" there is a request to update customer email address with a new one");
        var customerEmail = responseCustomer.getCustomerEmails().get(0);
        customerEmail.setEmail(StringUtils.generateRandomEmail());

        HttpResponse<JsonNode> updateEmailResponse = CustomerClient.putCustomerEmailData(
                responseCustomer.getCustomerNumber(), customerEmail);

        Steps.then(" the email address will be updated");
        assertThat(updateEmailResponse.getStatus(), Matchers.equalTo(HttpStatus.SC_OK));

        Steps.and(" when searching for email it will include new value");
        EmailEndpointAssertions responseAssertions = new EmailEndpointAssertions(
                CustomerClient.getEmailByCustomerNumber(responseCustomer.getCustomerNumber()));
        responseAssertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
        responseAssertions.assertEmailIsTheSameAsInRequest(customerEmail);

    }
}
