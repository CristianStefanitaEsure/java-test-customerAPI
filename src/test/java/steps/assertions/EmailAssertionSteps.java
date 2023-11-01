package steps.assertions;

import api.requests.CustomerClient;
import assertions.EmailEndpointAssertions;
import basic.BasicTest;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

import static org.hamcrest.Matchers.not;

@Slf4j
public class EmailAssertionSteps extends BasicTest {

    @Then("the email address will be updated")
    public void assertThatEmailAddressIsUpdated() {
        assertTest.assertTest(updateEmailResponse.getStatus(), Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Then("the request will be rejected with {int} code")
    public void assertThatUpdateEmailRequestIsRejected(Integer code) {
        assertTest.assertTest(updateEmailResponse.getStatus(), Matchers.equalTo(code));
    }

    @Then("the get Email by customerID response will include email data sent in request")
    public void assertEmailIsTheSameAsInRequest() {
        EmailEndpointAssertions emailAssertions = new EmailEndpointAssertions(CustomerClient.getEmailByCustomerNumber(customerId.get(0)));
        emailAssertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
        emailAssertions.assertEmailIsTheSameAsInRequest(customerResponse.getCustomerEmails().get(0));
    }


    @Then("the get Email by customerID response will return empty email")
    public void theGetEmailByCustomerIsEmpty() {
        EmailEndpointAssertions emailAssertions = new EmailEndpointAssertions(CustomerClient.getEmailByCustomerNumber(customerId.get(0)));
        emailAssertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
        emailAssertions.assertEmailIsEmpty(customerResponse.getCustomerEmails());
    }


    //IsEmailUniue Flag
    @Then("Email is the same as provided in the request")
    public void assertEmailIsTheSameAsInRequestForIsEmailUnique() {
        assertTest.assertTest("Email Address should match",
                customerResponse.getCustomerEmails().get(0).getEmail(), Matchers.equalToIgnoringCase(
                        customerRequest.getCustomerEmails().get(0).getEmail()));
    }

    @Then("Email is Empty for isEmailUnique testcase")
    public void assertEmailIsEmptyInRequestForIsEmailUnique() {
        assertTest.assertTest("Customer Email should not present",
                customerResponse.getCustomerEmails().isEmpty(), Matchers.is(true));
    }

    @Then("Email uniqueness should be {string}")
    public void assertEmailUniqueness(String value) {
        if (value.equalsIgnoreCase("true")) {
            assertTest.assertTest("Email uniqueness should be false",
                    customerResponse.getCustomerCreateUpdateWarnings().getIsEmailUnique(), Matchers.is(true));
        } else {
            assertTest.assertTest("Email uniqueness should be true",
                    customerResponse.getCustomerCreateUpdateWarnings().getIsEmailUnique(), Matchers.is(false));
        }
    }

    @Then("customer Email should not match")
    public void assertEmailNotEqual() {
        //customerEmail -email from first request , findOrCreateCustomerEmail- email from 2nd request
        assertTest.assertTest("Customer Email should not match",
                customerEmail.get(0), not(Matchers.equalTo(findOrCreateCustomerEmail.get(0))));
    }

    @Then("customer Email should match")
    public void assertEmail() {
        //customerEmail -email from first request , findOrCreateCustomerEmail- email from 2nd request
        assertTest.assertTest("Customer Email should match", customerEmail.get(0), Matchers.equalTo(findOrCreateCustomerEmail.get(0)));
        assertTest.assertTest("Email confirmed", customerResponse.getCustomerEmails().get(0).getEmailConfirmed(), Matchers.is(true));
    }

    @Then("customer Email should not present")
    public void assertEmailShouldNotPresent() {
        assertTest.assertTest("Customer Email should not present", findOrCreateCustomerEmail.isEmpty(), Matchers.is(true));
    }


}