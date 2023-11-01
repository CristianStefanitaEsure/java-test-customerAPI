package steps.customer.find;

import api.requests.CustomerClient;
import basic.BasicTest;
import httpclient.JsonRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import utils.ResponseUtils;
import utils.StringUtils;

import static api.requests.CustomerClient.getCustomerByEmail;
import static utils.EndpointsConfigurationData.*;

public class FindCustomerEmailSteps extends BasicTest {

    @Then("a user sends GET request to retrieve customer details by {string} email")
    public void getCustomerByEmailStep(String value) {
        switch (value.toLowerCase()) {
            case "valid" -> httpGetCustomerByEmailResponse = getCustomerByEmail(customerEmail.get(0));

            case "nonexisting" ->
                    httpGetCustomerByEmailResponse = getCustomerByEmail(StringUtils.generateRandomEmail());

            case "uppercase" -> httpGetCustomerByEmailResponse = getCustomerByEmail(customerEmail.get(0).toUpperCase());

            case "emailaddresswithwildcard" -> httpGetCustomerByEmailResponse = getCustomerByEmail(
                    customerEmail.get(0).replace(customerEmail.get(0).substring(5, 10), "*"));

            case "onlywildcard" -> httpGetCustomerByEmailResponse = getCustomerByEmail("*");

            case "updated" -> {
                var email = ResponseUtils.parseResponseToCustomer(
                                CustomerClient.getCustomerByCustomerNumber(ResponseUtils.extractCustomerNumber(httpResponse)))
                        .getCustomerEmails().get(0).getEmail();
                httpGetCustomerByEmailResponse = getCustomerByEmail(email);
            }
        }
    }

    @Given("there is a request to retrieve customer email for {string} customer")
    public void getCustomerEmailByNonExistingCustomerNumber(String value) {
        String customerId = "";
        if (value.equalsIgnoreCase("nonExisting")) {
            customerId = "imNotExistingHopefully";
        } else if (value.equalsIgnoreCase("wildcard")) {
            customerId = "*";
        }
        httpGetEmailByCustomerIDResponse = JsonRequest.get(CUSTOMER_API_URL + CUSTOMERS_PATH + "/" + customerId + EMAILS_SEARCH_BY_CUSTOMER_ID);
    }
}
