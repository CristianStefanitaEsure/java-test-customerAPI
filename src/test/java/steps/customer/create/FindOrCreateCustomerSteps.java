package steps.customer.create;

import api.model.CustomerRequestBuilder;
import api.requests.CustomerClient;
import basic.BasicTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import steps.customer.helper.CustomerManager;
import testdata.CustomerDataHolder;
import utils.ResponseUtils;
import utils.StringUtils;

import static utils.constants.CustomerBuilderData.generateRequiredCustomerData;

public class FindOrCreateCustomerSteps extends BasicTest {


    @Given("Create a customer {string} using FindorCreate endpoint")
    public void findOrCreateWithPooling(String flag) {
        CustomerDataHolder.generateRandomDynamicTestData();
        generateRequiredCustomerData();
        customerRequest = new CustomerRequestBuilder().createFullCustomerWithAllFields().build();

        if (flag.equalsIgnoreCase("withflag")) {
            httpResponse = CustomerManager.findOrCreateWithFlag(customerRequest);
        } else if (flag.equalsIgnoreCase("withoutflag")) {
            httpResponse = CustomerManager.findOrCreateWithPooling(customerRequest);
        }

        customerResponse = ResponseUtils.parseResponseToCustomer(httpResponse);
        customerId.add(customerResponse.getCustomerNumber());
    }


    @When("same customer request sent to FindorCreate endpoint updating {string} to {string}")
    public void sameRequestTofindOrCreateWithPooling(String attribute, String value) throws InterruptedException {
        Thread.sleep(5000);
        if (attribute.equalsIgnoreCase("FirstName")) {
            if (value.equalsIgnoreCase("null")) {
                customerResponse.getCustomerIndividualDetail().setFirstName(null);
            } else {
                customerResponse.getCustomerIndividualDetail().setFirstName(value);
            }
        } else if (attribute.equalsIgnoreCase("Email")) {
            switch (value.toLowerCase()) {
                case "null" -> customerResponse.getCustomerEmails().get(0).setEmail(null);
                case "new" -> customerResponse.getCustomerEmails().get(0).setEmail(StringUtils.generateRandomEmail());
                case "firstcustomersemail", "sameemail" ->
                        customerResponse.getCustomerEmails().get(0).setEmail(customerEmail.get(0));
                case "newcustomerwithexistingemail" -> {
                    customerResponse.getCustomerEmails().get(0).setEmail(customerEmail.get(0));
                    customerResponse.getCustomerIndividualDetail().setFirstName(StringUtils.generateRandomString(10));
                }
                default -> customerResponse.getCustomerEmails().get(0).setEmail(value);
            }
        }
        httpResponse = CustomerClient.findOrCreateWithFlag(customerResponse);

        if (httpResponse.getStatus() == 200 || httpResponse.getStatus() == 201) {
            customerResponse = ResponseUtils.parseResponseToCustomer(httpResponse);
            findOrCreateCustomerId.add(customerResponse.getCustomerNumber());

            if (!customerResponse.getCustomerEmails().isEmpty()) {
                findOrCreateCustomerEmail.add(customerResponse.getCustomerEmails().get(0).getEmail());
            }
        }
    }

}
