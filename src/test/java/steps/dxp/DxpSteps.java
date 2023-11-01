package steps.dxp;

import api.requests.CustomerClient;
import api.requests.DxpClient;
import assertions.DxpCustomerAssertions;
import assertions.LegitimateInterestAssertions;
import basic.BasicTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.http.HttpStatus;
import org.joda.time.DateTime;
import utils.ResponseUtils;

import static steps.customer.helper.CustomerManager.getCustomerRequest;

public class DxpSteps extends BasicTest {

    @Then("a request sent to DXP will return user's minimaldetails")
    public void DxpReturnsUsersMinimalDetails() {
        DxpCustomerAssertions assertions = new DxpCustomerAssertions(DxpClient.getCustomer(customerId.get(0)));
        assertions.compareMandatoryFieldsCustomerData(customerRequest);
        assertions.assertResponseResultsContainString("ES");
    }

    @Then("{string} will be {string} in the dxp response")
    public void latestPCWQuoteDateAssert(String attribute, String value) {
        DxpCustomerAssertions assertions = new DxpCustomerAssertions(DxpClient.getCustomer(customerId.get(0)));

        switch (value.toLowerCase()) {
            case "localdate" -> {
                value = new DateTime().toLocalDate().toString();
                assertions.assertValueIsPresentInObject(attribute, value);
            }
            case "null" -> assertions.assertValueIsPresentInObject(attribute, null);
            default -> assertions.assertValueIsPresentInObject(attribute, value);
        }

    }

    @Then("a request sent to DXP will return user's full details")
    public void DxpReturnsUsersFullDetails() {
        DxpCustomerAssertions assertions = new DxpCustomerAssertions(DxpClient.getCustomer(customerId.get(0)));
        assertions.compareMandatoryFieldsCustomerData(customerRequest);

        DxpCustomerAssertions dxpAssertions = new DxpCustomerAssertions(DxpClient.getCustomer(customerResponse.getCustomerNumber()));
        dxpAssertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);

        dxpAssertions.compareMandatoryFieldsCustomerData(customerResponse)
                .compareEmployment(customerResponse.getCustomerEmployments())
                .compareEmails(customerResponse.getCustomerEmails())
                .compareLegitimateInterest(customerResponse.getCustomerLegitimateInterestMarketingPermissions())
                .comparePhone(customerResponse.getCustomerPhones());
    }

    @And("a request to DXP to retrieve information will include legitimate interest details")
    public void validateIfLegitimateInterestIsDefaultedToProperValue() {
        LegitimateInterestAssertions assertions = new LegitimateInterestAssertions(
                CustomerClient.getCustomerByCustomerNumber(ResponseUtils.extractCustomerNumber(httpResponse)));
        assertions.extractPermissionsMap().marketingPreferencesExistingStructure();
    }

}
