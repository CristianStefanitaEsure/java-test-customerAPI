package steps.assertions;

import assertions.CompareCustomers;
import assertions.CustomerAssertions;
import basic.BasicTest;
import io.cucumber.java.en.Then;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import utils.ResponseUtils;

import static steps.customer.helper.CustomerManager.getCustomerRequest;

public class CustomerAssertionSteps extends BasicTest {

    @Then("{string} Customer response should be matched")
    public void assertCustomerResponse(String value) {
        switch (value.toLowerCase()) {
            case "minimal" -> CompareCustomers.assertMinimalCustomerResponseIsTheSame(customerRequest,
                    ResponseUtils.parseResponseToCustomer(httpResponse));
            case "full" -> CompareCustomers.assertCustomerResponseIsTheSame(customerRequest,
                    ResponseUtils.parseResponseToCustomer(httpResponse));
        }
    }

    @Then("{int} response code should be received from the Update Customer details response")
    public void assertThatUpdateCustomerDetailsResponseCode(Integer code) {
        CustomerAssertions customerAssertions = new CustomerAssertions(modifyCustomerDetailsResponse);
        customerAssertions.assertResponseReceivedExpectedCode(code);
    }

    @Then("customer Id should match")
    public void assertCustomerID() {
        //customerId -CustomerId from first request , findOrCreateCustomerId- CustomerId from 2nd request
        assertTest.assertTest("customer Id should match", customerId, Matchers.equalTo(findOrCreateCustomerId));
    }

    @Then("Customer Creation Response should contain isEmailUnique flag {string}")
    public void assertIsEmailUnique(String expectedValue) {
        assertTest.assertTest("isEmailUnique should match",
                customerResponse.getCustomerCreateUpdateWarnings().getIsEmailUnique().toString(), Matchers.equalTo(expectedValue));
    }

    @Then("the customer data will be modified")
    public void assertCustomersUpdatedData() {
        CustomerAssertions customerAssertions = new CustomerAssertions(modifyCustomerDetailsResponse);
        customerAssertions.assertCustomerRequestIsSimilarToResponse(updatedCustomer);
    }

    @Then("response result will include customer bars associated with the customer")
    public void assertBarsDataIsCorrect() {
        CustomerAssertions assertions = new CustomerAssertions(httpGetByCustomerIDResponse);
        assertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
    }

    @Then("the update customer's email response contains a message: {string}")
    public void assertMessagePresentInUpdateCustomersEmailResponse(String expectedReferralCode) {
        CustomerAssertions objToCheck = null;
        if (updateEmailResponse != null) {
            objToCheck = new CustomerAssertions(updateEmailResponse);
        } else if (modifyCustomerDetailsResponse != null) {
            objToCheck = new CustomerAssertions(modifyCustomerDetailsResponse);
        }

        objToCheck.assertErrorMessageValueContent(expectedReferralCode);
    }

    @Then("a request to get customer details to java api returns a valid Emails And Phones")
    public void verifyEmailsAndPhonesAndConfirmationsForNewCustomer() {
        CustomerAssertions assertions = new CustomerAssertions(httpGetByCustomerIDResponse);
        assertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
        assertions.assertThatResponseHasEmailsDataInResults(customerGetByCustomerIDResponse.getCustomerEmails());
        assertions.assertThatResponseHasPhonesDataInResults(customerGetByCustomerIDResponse.getCustomerPhones());
    }

}
