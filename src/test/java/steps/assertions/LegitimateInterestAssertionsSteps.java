package steps.assertions;

import api.model.LegitimateInterestBuilder;
import api.requests.CustomerClient;
import assertions.LegitimateInterestAssertions;
import basic.BasicTest;
import io.cucumber.java.en.Then;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

public class LegitimateInterestAssertionsSteps extends BasicTest {
    @Then("LI response will include values set for the legitimate interest")
    public void assertLIResponse() {
        LegitimateInterestAssertions interestAssertions = new LegitimateInterestAssertions(
                CustomerClient.getCustomerByCustomerNumber(customerId.get(0)));
        interestAssertions.extractPermissionsMap().compareValuesWithRequest(customerLegitimateInterest);
    }

    @Then("LI response will include default values of legitimate interest")
    public void assertDefaultLIResponse() {
        customerLegitimateInterest = new LegitimateInterestBuilder().create(true).setChannelsWithTheSameSourceValue("DEFAULT").build();
        LegitimateInterestAssertions interestAssertions = new LegitimateInterestAssertions(
                CustomerClient.getCustomerByCustomerNumber(customerId.get(0)));
        interestAssertions.extractPermissionsMap().compareValuesWithRequest(customerLegitimateInterest);
    }

    @Then("the Update LI request will be rejected with {int} code")
    public void assertThatUpdateLIRequestIsRejected(Integer code) {
        assertTest.assertTest(modifyCustomerLegitimateInterest.getStatus(), Matchers.equalTo(code));
    }

    @Then("a request to get customer details to java api returns a valid LI data")
    public void verifyLegitimateInterestIsPresentForGetCustomer() {
        LegitimateInterestAssertions assertions = new LegitimateInterestAssertions(httpGetByCustomerIDResponse);
        assertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
        assertions.extractPermissionsMap().marketingPreferencesExistingStructure();
    }
}
