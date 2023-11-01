package steps.customer.update;

import api.model.Customer;
import api.model.CustomerRequestBuilder;
import api.requests.CustomerClient;
import basic.BasicTest;
import io.cucumber.java.en.When;
import utils.ResponseUtils;
import utils.StringUtils;

import java.util.ArrayList;

public class UpdateCustomerSteps extends BasicTest {

    @When("there is a request to update customer email address with a {string}")
    public void putCustomerEmailData(String value) {
        var customerEmail = customerResponse.getCustomerEmails().get(0);

        switch (value.toLowerCase()) {
            case "newone" -> customerEmail.setEmail(StringUtils.generateRandomEmail());
            case "invalid" -> customerEmail.setEmail(StringUtils.generateRandomString(15));
            case "empty" -> customerEmail.setEmail("");
            case "null" -> customerEmail.setEmail(null);
        }
        updateEmailResponse = CustomerClient.putCustomerEmailData(customerResponse.getCustomerNumber(), customerEmail);
    }

    @When("a user sends PUT request to modify the customer with {string}")
    public void updateCustomersDetailsRequest(String operation) {
        switch (operation.toLowerCase()) {
            case "fulldetails" -> {
                updatedCustomer = bodyFactory.fullModifiedCustomer(httpResponse);
                modifyCustomerDetailsResponse = CustomerClient.updateAnExistingCustomerWithGivenDetails(updatedCustomer);
            }
            case "bars" -> {
                customerResponse = bodyFactory.modifyCustomerWithBars(customerResponse);
                modifyCustomerDetailsResponse = CustomerClient.updateAnExistingCustomerWithGivenDetails(customerResponse);
            }
            case "removedbars" -> {
                customerResponse = bodyFactory.modifyCustomerWithBars(ResponseUtils.parseResponseToCustomer(modifyCustomerDetailsResponse));
                modifyCustomerDetailsResponse = CustomerClient.updateAnExistingCustomerWithGivenDetails(customerResponse);
            }
            case "incorrectfirstname" -> {
                updatedCustomer = bodyFactory.fullModifiedCustomerIncorrectNames(httpResponse);
                modifyCustomerDetailsResponse = CustomerClient.updateAnExistingCustomerWithGivenDetails(updatedCustomer);
            }
        }
    }

    @When("there is a request to update customer email ID with a {string}")
    public void putCustomerEmailIDData(String value) {
        var customerEmail = customerResponse.getCustomerEmails().get(0);
        switch (value.toLowerCase()) {
            case "invalid" -> customerEmail.setId(StringUtils.generateRandomString(30));
            case "empty" -> customerEmail.setId("");
        }
        updateEmailResponse = CustomerClient.putCustomerEmailData(customerResponse.getCustomerNumber(), customerEmail);
    }

    @When("there is a request to update customer email with EmailConfirmed as {string}")
    public void putCustomerEmailConfirmedField(String value) {
        var customerEmail = customerResponse.getCustomerEmails().get(0);

        if (value.equalsIgnoreCase("null")) {
            customerEmail.setEmailConfirmed(null);
        }
        updateEmailResponse = CustomerClient.putCustomerEmailData(customerResponse.getCustomerNumber(), customerEmail);
    }


    @When("Update the email address for {string}")
    public void changeCustomersEmail(String customerNumber) {
        var customerEmail = customerGetByCustomerIDResponse.getCustomerEmails().get(0);
        customerEmail.setEmail(StringUtils.generateRandomEmail());
        customerEmail.setEmailConfirmed(true);
        updateEmailResponse = CustomerClient.putCustomerEmailData(customerNumber, customerEmail);
    }

    @When("a user sends PUT request to modify the customer with {string} as {string}")
    public void putCustomerPhonesDifferentData(String type, String value) {
        updatedCustomer = buildUpdateCustomerTpye(type, value, customerResponse);

        modifyCustomerDetailsResponse = CustomerClient.updateAnExistingCustomerWithGivenDetails(updatedCustomer);
    }

    private Customer buildUpdateCustomerTpye(String type, String value, Customer customerResponse) {
        Customer updateCustomer = new Customer();
        switch (type.toLowerCase()) {
            case "customeremails" -> updateCustomer = buildUpdateCustomerEmail(value, customerResponse);
            case "customerphones" -> updateCustomer = buildUpdateCustomerPhones(value, customerResponse);
            case "customeremployments" -> updateCustomer = buildUpdateCustomerEmployments(value, customerResponse);
            case "brands" -> updateCustomer = buildUpdateCustomerBrands(value, customerResponse);
            case "customeraddress" -> updateCustomer = buildUpdateCustomerAddress(value, customerResponse);
            case "customerindividualdetail" ->
                    updateCustomer = buildUpdateCustomerIndividualDetail(value, customerResponse);
            case "customerlegitimateinterestmarketingpermissions" ->
                    updateCustomer = buildUpdateCustomerLegitimateInterestMarketingPermissions(value, customerResponse);
            case "customerbars" -> updateCustomer = buildUpdateCustomerBars(value, customerResponse);
        }
        return updateCustomer;
    }

    private Customer buildUpdateCustomerEmail(String value, Customer customerResponse) {
        Customer updateCustomer = new Customer();
        switch (value.toLowerCase()) {
            case "invalid" -> {
                value = StringUtils.generateRandomString(30);
                updateCustomer = bodyFactory.modifyCustomerWithEmail(customerResponse, value);
            }
            case "valid" ->
                    updateCustomer = bodyFactory.modifyCustomerWithEmail(customerResponse, StringUtils.generateRandomEmail());
            case "missing" -> updateCustomer = bodyFactory.createFullCustomerWithoutEmail(customerResponse);
            case "empty" -> updateCustomer = bodyFactory.modifyCustomerWithEmail(customerResponse, "");
            case "null" -> updateCustomer = bodyFactory.modifyCustomerWithEmail(customerResponse, null);
            default -> updateCustomer = bodyFactory.modifyCustomerWithEmail(customerResponse, value);
        }
        return updateCustomer;
    }

    private Customer buildUpdateCustomerPhones(String value, Customer customerResponse) {
        Customer updateCustomer = new Customer();

        switch (value.toLowerCase()) {
            case "valid" ->
                    updateCustomer = customerResponse.setCustomerPhones(new CustomerRequestBuilder().createCustomerPhones());
            case "missing" -> updateCustomer = bodyFactory.createFullCustomerWithoutCustomerPhones(customerResponse);
            case "empty" -> updateCustomer = customerResponse.setCustomerPhones(new ArrayList<>());
            case "multiplevalues" -> updateCustomer = bodyFactory.createFullCustomerWithMultipleCustomerPhones(customerResponse);
        }
        return updateCustomer;
    }

    private Customer buildUpdateCustomerEmployments(String value, Customer customerResponse) {
        Customer updateCustomer = new Customer();
        switch (value.toLowerCase()) {
            case "valid" ->
                    updateCustomer = customerResponse.setCustomerEmployments(new CustomerRequestBuilder().createEmployments());
            case "missing" -> updateCustomer = bodyFactory.createFullCustomerWithoutEmployments(customerResponse);
            case "empty" -> updateCustomer = customerResponse.setCustomerEmployments(new ArrayList<>());
            case "multiplevalues" -> updateCustomer = bodyFactory.createFullCustomerWithMultipleCustomerEmployments(customerResponse);
        }
        return updateCustomer;
    }

    private Customer buildUpdateCustomerBrands(String value, Customer customerResponse) {
        Customer updateCustomer = new Customer();
        if (value.equalsIgnoreCase("missing")) {
            updateCustomer = bodyFactory.createFullCustomerWithoutBrands(customerResponse);
        }
        return updateCustomer;
    }

    private Customer buildUpdateCustomerBars(String value, Customer customerResponse) {
        Customer updateCustomer = new Customer();
        if (value.equalsIgnoreCase("missing")) {
            updateCustomer = bodyFactory.createFullCustomerWithoutCustomerBars(customerResponse);
        }
        return updateCustomer;
    }

    private Customer buildUpdateCustomerAddress(String value, Customer customerResponse) {
        Customer updateCustomer = new Customer();
        if (value.equalsIgnoreCase("missing")) {
            updateCustomer = bodyFactory.createFullCustomerWithoutCustomerAddress(customerResponse);
        }
        return updateCustomer;
    }

    private Customer buildUpdateCustomerIndividualDetail(String value, Customer customerResponse) {
        Customer updateCustomer = new Customer();
        if (value.equalsIgnoreCase("missing")) {
            updateCustomer = bodyFactory.createFullCustomerWithoutCustomerIndividualDetail(customerResponse);
        }
        return updateCustomer;
    }


    private Customer buildUpdateCustomerLegitimateInterestMarketingPermissions(String value, Customer customerResponse) {
        Customer updateCustomer = new Customer();
        if (value.equalsIgnoreCase("missing")) {
            updateCustomer = bodyFactory.createFullCustomerWithoutCustomerLegitimateInterestMarketingPermissions(customerResponse);
        }
        return updateCustomer;
    }

}
