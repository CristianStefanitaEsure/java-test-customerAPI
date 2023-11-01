package steps.assertions;

import assertions.CustomerAssertions;
import assertions.SearchAssertions;
import basic.BasicTest;
import io.cucumber.java.en.Then;
import org.apache.http.HttpStatus;
import testdata.StringRepository;

public class FindCustomerAssertionSteps extends BasicTest {

    @Then("the search customer response is empty")
    public void assertThatSearchCustomerResponseHasEmpty() {
        SearchAssertions assertions = new SearchAssertions(httpGetCustomerIDBySearchAPIResponse);
        assertions.assertThatResponseIsEmpty();
    }

    @Then("a request to get customer by email to java api returns a valid response")
    public void searchForCustomerUsingExactSameEmailAddress() {
        CustomerAssertions assertions = new CustomerAssertions(httpGetCustomerByEmailResponse);
        assertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
        assertions.assertResponseResultsContainString(customerId.get(0));
    }

    @Then("a request to get customer by email to java api returns a empty customer")
    public void assertThatHetCustomerByEmailResponseIsEmpty() {
        CustomerAssertions assertions = new CustomerAssertions(httpGetCustomerByEmailResponse);
        assertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
        assertions.assertThatResponseIsEmpty();
    }

    @Then("a user will receive customer id in the search customer response body")
    public void assertThatSearchCustomerResponseHasDataInResults() {
        SearchAssertions assertions = new SearchAssertions(httpGetCustomerIDBySearchAPIResponse);
        assertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
        assertions.assertThatResponseHasDataInResults(customerId.get(0));
    }

    @Then("{int} response code should be received by getEmail by customerId api")
    public void getEmailByCustomerNumberWithInvalidCustomerId(Integer code) {
        CustomerAssertions assertions = new CustomerAssertions(httpGetEmailByCustomerIDResponse);
        assertions.assertThatResponseReceivedExpectedCode(code);
        assertions.assertErrorMessageValueContent(StringRepository.FAILED_GET_EMAIL);
    }

    @Then("a request to get customer details to java api returns a valid response")
    public void getCustomerByCustomerNumberWithProperCustomerId() {
        CustomerAssertions assertions = new CustomerAssertions(httpGetByCustomerIDResponse);
        assertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
    }

    @Then("{int} response code should be received by getcustomer api")
    public void getCustomerByCustomerNumberWithInvalidCustomerId(Integer code) {
        CustomerAssertions assertions = new CustomerAssertions(httpGetByCustomerIDResponse);
        assertions.assertThatResponseReceivedExpectedCode(code);
    }

}
