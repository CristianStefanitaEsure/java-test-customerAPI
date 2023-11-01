package steps.assertions;

import assertions.CustomerAssertions;
import basic.BasicTest;
import io.cucumber.java.en.Then;

public class ApiAssertionSteps extends BasicTest {
    @Then("{int} response code should be received")
    public void assertResponseCodes(int expectedResponseCode) {
        new CustomerAssertions(httpResponse).assertResponseReceivedExpectedCode(expectedResponseCode);
    }

    @Then("the response contains a message: {string}")
    public void assertReferralMessagePresent(String expectedReferralCode) {
        new CustomerAssertions(httpResponse).assertErrorMessageValueContent(expectedReferralCode);
    }

    @Then("a getcustomer response contains a message: {string}")
    public void assertGetcustomerResponseContains(String expectedReferralCode) {
        new CustomerAssertions(httpGetByCustomerIDResponse).assertErrorMessageValueContent(expectedReferralCode);
    }
}
