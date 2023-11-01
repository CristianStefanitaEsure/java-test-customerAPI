package steps.customer.create;

import api.model.Customer;
import api.model.CustomerRequestBuilder;
import api.requests.CustomerClient;
import api.requests.customervisitor.FullCustomerNoHouseNameVisitor;
import basic.BasicTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import steps.customer.helper.CustomerManager;
import testdata.CustomerDataHolder;
import utils.ResponseUtils;
import utils.StringUtils;

import static utils.constants.CustomerBuilderData.generateRequiredCustomerData;

public class CreateCustomerSteps extends BasicTest {

    @Given("Create a customer with null values for mandatory attribute")
    public void nullValuesForCustomerAPIValidatedFields() {
        httpResponse = CustomerClient.createCustomer(bodyFactory.nullValuesForCustomerAPIValidatedFields());
    }

    @Given("Create a customer with empty brands attribute")
    public void validateIfCustomerIsRejectedWithMissingBrandsValue() {
        httpResponse = CustomerClient.createCustomer(bodyFactory.missingBrands());
    }

    @Given("Create a customer with empty HouseName and HouseNumber attribute")
    public void customerWithEmptyHouseNameHouseNumber() {
        httpResponse = CustomerClient.createCustomer(bodyFactory.customerWithEmptyHouseNameHouseNumber());
    }

    @Given("Create a customer to validate Maximum Length For FirstName And LastName Fields")
    public void validateMaximumLengthForFirstAndLastNameFields() {
        httpResponse = CustomerClient.createCustomer(bodyFactory.customerWithCustomFirstAndLastName(
                StringUtils.generateRandomString(60), StringUtils.generateRandomString(60)));
    }

    @Given("Creates a customer with {string} fields")
    public void createCustomerWithRequiredFields(String requestType) {
        CustomerDataHolder.generateRandomDynamicTestData();
        generateRequiredCustomerData();

        switch (requestType.toLowerCase()) {
            case "required" -> {
                customerRequest = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();
                httpResponse = CustomerManager.createCustomer(customerRequest);
                customerResponse = ResponseUtils.parseResponseToCustomer(httpResponse);
                customerId.add(customerResponse.getCustomerNumber());
            }
            case "all" -> {
                customerRequest = new CustomerRequestBuilder().createFullCustomerWithAllFields().build();
                httpResponse = CustomerManager.createCustomer(customerRequest);
                customerResponse = ResponseUtils.parseResponseToCustomer(httpResponse);
                customerId.add(customerResponse.getCustomerNumber());
                customerEmail.add(customerResponse.getCustomerEmails().get(0).getEmail());
            }
            case "nohousename" -> {
                customerRequest = new CustomerRequestBuilder().createFullCustomerWithAllFields().build();
                customerRequest.accept(new FullCustomerNoHouseNameVisitor());
                httpResponse = CustomerClient.createCustomer(customerRequest);
                customerResponse = ResponseUtils.parseResponseToCustomer(httpResponse);
                customerId.add(customerResponse.getCustomerNumber());
                customerEmail.add(customerResponse.getCustomerEmails().get(0).getEmail());
            }
            case "nohousenumber" -> {
                customerRequest = new CustomerRequestBuilder().createFullCustomerWithAllFields().build();
                httpResponse = CustomerClient.createCustomer(customerRequest);
                customerResponse = ResponseUtils.parseResponseToCustomer(httpResponse);
                customerId.add(customerResponse.getCustomerNumber());
                customerEmail.add(customerResponse.getCustomerEmails().get(0).getEmail());
            }
        }
    }

    @When("same customer request sent to FindorCreate endpoint")
    public void sameRequestTofindOrCreateWithPooling() {
        httpResponse = CustomerClient.findOrCreateWithPoolingAndFlag(customerResponse);
        if (httpResponse.getStatus() == 200 || httpResponse.getStatus() == 201) {
            customerResponse = ResponseUtils.parseResponseToCustomer(httpResponse);
            findOrCreateCustomerId.add(customerResponse.getCustomerNumber());
            findOrCreateCustomerEmail.add(customerResponse.getCustomerEmails().get(0).getEmail());
        }
    }

    @Given("Create a customer with empty values for mandatory attribute")
    public void emptyCustomerAPIValidatedFields() {
        httpResponse = CustomerClient.createCustomer(bodyFactory.emptyCustomerAPIValidatedFields());
    }

}