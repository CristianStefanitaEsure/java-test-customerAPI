package steps.assertions;

import api.requests.CustomerClient;
import assertions.CustomerAssertions;
import assertions.CustomerBarsAssertions;
import basic.BasicTest;
import io.cucumber.java.en.Then;
import org.apache.http.HttpStatus;

public class BarAssertionSteps extends BasicTest {

    @Then("the customer {string} bars data will match requested data")
    public void assertBarsDataIsCorrect(String value) {
        CustomerBarsAssertions assertions = new CustomerBarsAssertions(CustomerClient.getCustomerByCustomerNumber(customerId.get(0)));
        assertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);

        switch (value.toLowerCase()) {
            case "full" -> assertions.assertBarsDataIsCorrect(bars.get(0));
            case "minimal" -> assertions.assertMinimalBarsDataIsCorrect(bars.get(0));
            case "removed" -> {
                assertions.assignRequestedBarForAssertion(barsFromResponse.get(0));
                assertions.assertBarIdIsCorrect().assertRemoveOtherIsAsExpected();
            }
            case "modified" -> assertions.assertBarsDataIsCorrect(customerResponse.getCustomerBars().get(0));
        }
    }

    @Then("putBar response should contain massage : {string}")
    public void assertPutBarResponseMessage(String message) {
        CustomerAssertions assertions = new CustomerAssertions(customerBarResponse);
        assertions.assertErrorMessageValueContent(message);
    }

    @Then("{int} response code should be received by putBar api")
    public void putBarAssertion(Integer code) {
        CustomerAssertions assertions = new CustomerAssertions(customerBarResponse);
        assertions.assertResponseReceivedExpectedCode(code);
    }

    @Then("the customer second bars data will match requested data")
    public void assertSecondBarsDataIsCorrect() {
        CustomerBarsAssertions assertions = new CustomerBarsAssertions(CustomerClient.getCustomerByCustomerNumber(customerId.get(0)));
        assertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
        assertions.assertMinimalBarFromList(secondbars.get(0));
    }
}
