package assertions;

import api.model.Customer;
import api.model.customernodes.CustomerBar;
import api.model.customernodes.CustomerEmail;
import api.model.customernodes.CustomerPhone;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.hamcrest.Matchers;
import utils.FileLoader;
import utils.ResponseUtils;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class CustomerAssertions extends CustomerCommonAssertions {

    static AssertTest assertTest;
    
    public CustomerAssertions(HttpResponse<JsonNode> response) {
        super(response);
    }

    public CustomerAssertions assertCustomerRequestIsSimilarToResponse(Customer request) {
        CompareCustomers.assertCustomerResponseIsTheSame(request,
                ResponseUtils.parseResponseToCustomer(response));

        return this;
    }

    public CustomerAssertions assertResponseReceivedExpectedCode(int expectedCode) {
        assertThatResponseReceivedExpectedCode(expectedCode);
        return this;
    }

    public CustomerAssertions assertNoEmailsPresentForCustomer() {
        assertTest.assertTest(
                ResponseUtils.parseResponseToCustomer(response).getCustomerEmails().isEmpty(),
                Matchers.is(true));
        return this;
    }

    public CustomerAssertions assertThatEmailUniquenessHasValue(Boolean expectedValue) {
        assertTest.assertTest(
                ResponseUtils.parseResponseToCustomer(response).getCustomerCreateUpdateWarnings()
                        .getIsEmailUnique(), Matchers.is(expectedValue));

        return this;
    }

    public CustomerAssertions assertResponseResultsContainString(String expectedString) {
        assertTest.assertTest(response.getBody().toString().contains(expectedString), Matchers.is(true));
        return this;
    }

    public CustomerAssertions assertBarsDataIsCorrect(String barJsonPath) {
        CustomerBar requestBar = FileLoader.asClass(barJsonPath, CustomerBar.class);
        CustomerBar responseBar = ResponseUtils.parseResponseToCustomer(response)
                .getCustomerBars().get(0);

        assertTest.assertTest(requestBar.getBarType().equals(responseBar.getBarType()), Matchers.is(true));
        assertTest.assertTest(requestBar.getCategory().equals(responseBar.getCategory()), Matchers.is(true));
        assertTest.assertTest(requestBar.getDescription().equals(responseBar.getDescription()),
                Matchers.is(true));
        assertTest.assertTest(requestBar.getReason().equals(responseBar.getReason()), Matchers.is(true));
        assertTest.assertTest(requestBar.getPolicyNumber().equals(responseBar.getPolicyNumber()),
                Matchers.is(true));

        return this;
    }

    public CustomerAssertions assertThatResponseHasEmailsDataInResults(
            List<CustomerEmail> postResponseCustomerEmails) {
        for (int i = 0; i < postResponseCustomerEmails.size(); i++) {

            assertThatResponseHasDataInResults(postResponseCustomerEmails.get(i).getEmail());
            assertThatResponseHasDataInResults(
                    postResponseCustomerEmails.get(i).getEmailConfirmed().toString());
        }

        return this;
    }

    public CustomerAssertions assertThatResponseHasPhonesDataInResults(
            List<CustomerPhone> postResponseCustomerPhones) {
        for (int i = 0; i < postResponseCustomerPhones.size(); i++) {

            assertThatResponseHasDataInResults(postResponseCustomerPhones.get(i).getPhoneNumber());
            assertThatResponseHasDataInResults(
                    postResponseCustomerPhones.get(i).getPhoneConfirmed().toString());
        }

        return this;
    }

}
