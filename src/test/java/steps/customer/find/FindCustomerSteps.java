package steps.customer.find;

import api.model.CustomerSearchRequestBuilder;
import api.requests.CustomerClient;
import basic.BasicTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.ResponseUtils;

public class FindCustomerSteps extends BasicTest {

    @Then("customer is available for customer search")
    public void customerIsAvailableForSearch() {
        var searchRequest = new CustomerSearchRequestBuilder().create().setSearchFromCustomerObject(customerResponse).build();
        CustomerClient.searchCustomerUntilSuccessOrTimeout(searchRequest);
    }

    @Given("a user sends GET request to retrieve customer details with {string} ID")
    public void getCustomerByInvalidCustomerNumber(String id) {
        httpGetByCustomerIDResponse = CustomerClient.getCustomerByCustomerNumber(id);
    }

    @When("get a customers Email ID {string}")
    public void getCustomersEmailID(String customerNumber) {
        httpGetByCustomerIDResponse = CustomerClient.getCustomerByCustomerNumber(customerNumber);
        customerGetByCustomerIDResponse = ResponseUtils.parseResponseToCustomer(httpGetByCustomerIDResponse);
    }

    @Then("a user sends GET request to retrieve email by {string} customerID")
    public void getCustomerEmailByCustomerNumber(String value) {
        if (value.equalsIgnoreCase("valid")) {
            httpGetEmailByCustomerIDResponse = CustomerClient.getEmailByCustomerNumber(customerId.get(0));
        }
    }

    @Then("a user sends GET request to retrieve customer details by customer number")
    public void getCustomerByCustomerNumber() {
        httpGetByCustomerIDResponse = CustomerClient.getCustomerByCustomerNumber(customerId.get(0));
        customerGetByCustomerIDResponse = ResponseUtils.parseResponseToCustomer(httpGetByCustomerIDResponse);
    }

}
