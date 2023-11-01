package steps.assertions;

import api.model.Customer;
import api.requests.CustomerClient;
import assertions.EmailEndpointAssertions;
import basic.BasicTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import utils.ResponseUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;

@Slf4j
public class GenericAssertionSteps extends BasicTest {
    @Then("the get by customerID response will return {string} value for {string}")
    public void assertCustomerFieldIsEmpty(String isEmpty, String key) {
        EmailEndpointAssertions emailAssertions = new EmailEndpointAssertions(CustomerClient.getCustomerByCustomerNumber(customerId.get(0)));
        emailAssertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);

        Customer customerFound = ResponseUtils.parseResponseToCustomer(emailAssertions.getResponse());
        var value = getCustomerField(key, customerFound);

        String msg = "response with key " + key + " + has value " + value + " -> " + isEmpty;
        assertTest.assertTest(msg, ((ArrayList) value).isEmpty(), Matchers.is(isEmpty.equalsIgnoreCase("empty")));
    }


    @And("the body response for updated request has {string} value for {string}")
    public void theBodyResponseHasValueFor(String isEmpty, String key) {
        Customer customerFound = ResponseUtils.parseResponseToCustomer(modifyCustomerDetailsResponse);
        var value = getCustomerField(key, customerFound);

        String msg = "response with key " + key + " + has value " + value + " -> " + isEmpty;
        assertTest.assertTest(msg, ((ArrayList) value).isEmpty(), Matchers.is(isEmpty.equalsIgnoreCase("empty")));
    }

    private Object getCustomerField(String key, Customer customerFound) {
        try {
            Class<Customer> person = (Class<Customer>) Class.forName("api.model.Customer");
            Field privateField = person.getDeclaredField(key);
            privateField.setAccessible(true);
            var value = (Object) privateField.get(customerFound);
            return value;
        } catch (Exception e) {
            log.error("Exception {}", e);
        }
        return null;
    }


}
