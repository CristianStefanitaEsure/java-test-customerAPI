package steps.customer.find;

import api.model.CustomerSearch;
import api.requests.CustomerClient;
import api.requests.SearchFactory;
import api.requests.customersearchvisitor.*;
import basic.BasicTest;
import io.cucumber.java.en.When;

import static api.requests.JsonCustomerRequestType.CUSTOMER_SEARCH;

public class FindCustomerPostSteps extends BasicTest {

    @When("a user sends a POST request to retrieve customerID {string} using search API")
    public void testCustomerSearchPostRequest(String value) {
        switch (value.toLowerCase()) {
            case "withhousenumber", "withouthousename" -> {
                CustomerSearch searchBody = SearchFactory.create(CUSTOMER_SEARCH);
                searchBody.accept(new NoHouseNameSearchVisitor());
                httpGetCustomerIDBySearchAPIResponse = CustomerClient.searchCustomerUntilSuccessOrTimeout(searchBody);
            }
            case "withhousenumberandhousename" -> {
                CustomerSearch searchBody = SearchFactory.create(CUSTOMER_SEARCH);
                searchBody.accept(new AllDetailsSearchVisitor());
                httpGetCustomerIDBySearchAPIResponse = CustomerClient.searchCustomerUntilSuccessOrTimeout(searchBody);
            }
            case "withouthousenumber" -> {
                CustomerSearch searchBody = SearchFactory.create(CUSTOMER_SEARCH);
                searchBody.accept(new NoHouseNumberSearchVisitor());
                httpGetCustomerIDBySearchAPIResponse = CustomerClient.searchCustomerUntilSuccessOrTimeout(searchBody);
            }
            case "incorrecthousename" -> {
                CustomerSearch searchBody = SearchFactory.create(CUSTOMER_SEARCH);
                searchBody.accept(new IncorrectHouseNameSearchVisitor());
                httpGetCustomerIDBySearchAPIResponse = CustomerClient.searchForACustomerNumberByExactMatch(searchBody);
            }
            case "incorrecthousenumber" -> {
                CustomerSearch searchBody = SearchFactory.create(CUSTOMER_SEARCH);
                searchBody.accept(new IncorrectHouseNumberSearchVisitor());
                httpGetCustomerIDBySearchAPIResponse = CustomerClient.searchForACustomerNumberByExactMatch(searchBody);
            }
            case "withincorrectdetails" -> httpGetCustomerIDBySearchAPIResponse =
                    CustomerClient.searchForACustomerNumberByExactMatch(bodyFactory.noResultsCustomerSearchBody());
        }
    }

}
