package api.requests;

import api.model.Customer;
import api.model.CustomerRequestBuilder;
import api.model.CustomerSearch;
import api.model.CustomerSearchRequestBuilder;
import api.model.customernodes.CustomerEmployment;
import api.model.customernodes.CustomerPhone;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import utils.ResponseUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BodyFactory {

    public Customer fullCustomer() {
        return new CustomerRequestBuilder().createFullCustomerWithAllFields()
                .setUniqueFirstAndLastName().setUniqueHouseName().build();
    }

    public Customer customerWithCustomFirstAndLastName(String firstName, String lastName) {
        return new CustomerRequestBuilder().createFullCustomerWithAllFields()
                .setCustomerFirstNameToValue(firstName)
                .setCustomerLastNameToValue(lastName)
                .build();
    }

    public Customer minimumFieldsCustomer() {
        return new CustomerRequestBuilder().createCustomerWithRequiredInfo()
                .setUniqueFirstAndLastName().setUniqueHouseName().build();
    }

    public Customer emptyCustomerAPIValidatedFields() {

        return new CustomerRequestBuilder().createFullCustomerWithAllFields()
                .setCustomerFirstNameToValue("")
                .setCustomerLastNameToValue("").setCustomerPostcodeToValue("").setDateOfBirthToValue("")
                .build();
    }

    public Customer nullValuesForCustomerAPIValidatedFields() {

        return new CustomerRequestBuilder().createFullCustomerWithAllFields()
                .setCustomerFirstNameToValue(null)
                .setCustomerLastNameToValue(null).setCustomerPostcodeToValue(null)
                .setDateOfBirthToValue(null).build();
    }

    public Customer missingBrands() {

        return new CustomerRequestBuilder().createFullCustomerWithAllFields()
                .setBrands(Collections.emptyList()).build();
    }

    public CustomerSearch noResultsCustomerSearchBody() {

        return new CustomerSearchRequestBuilder().create("incorrectcustomersearch.json").build();
    }

    public Customer customerWithEmptyHouseNameHouseNumber() {

        return new CustomerRequestBuilder().createFullCustomerWithAllFields()
                .setUniqueFirstAndLastName().setEmptyHouseNameAndHouseNumber().build();
    }

    public Customer fullModifiedCustomer(HttpResponse<JsonNode> response) {
        Customer customerResponse = ResponseUtils.parseResponseToCustomer(response);

        return new CustomerRequestBuilder().createFullCustomerWithAllFields()
                .setCustomerEmailDataToValue(customerResponse.getCustomerEmails().get(0))
                .setCustomerNumber(customerResponse.getCustomerNumber())
                .build();
    }

    public Customer fullModifiedCustomerIncorrectNames(HttpResponse<JsonNode> response) {
        Customer customer = ResponseUtils.parseResponseToCustomer(response);
        customer.getCustomerIndividualDetail().setFirstName("124124124");
        return customer;
    }

    public Customer customerWithModifiedEmail(String emailAddress) {
        return new CustomerRequestBuilder().createFullCustomerWithAllFields()
                .setUniqueFirstAndLastName()
                .setCustomerEmailToValue(emailAddress).build();
    }

    public Customer modifyCustomerWithBars(Customer customer) {
        return new CustomerRequestBuilder().create(customer).setBars().build();
    }

    public Customer modifyCustomerWithRemoveBars(Customer customer) {
        return new CustomerRequestBuilder().create(customer).setRemovedBars().build();
    }

    public Customer modifyCustomerWithEmail(Customer customer, String emailAddress) {
        return new CustomerRequestBuilder().create(customer).setCustomerEmailToValue(emailAddress).build();
    }

    public Customer createFullCustomerWithoutEmail(Customer customer) {

        return new Customer(customer.getBrands(), customer.getCustomerNumber())
                .setCustomerBars(customer.getCustomerBars())
                .setCustomerAddress(customer.getCustomerAddress())
                .setCustomerEmployments(customer.getCustomerEmployments())
                .setCustomerIndividualDetail(customer.getCustomerIndividualDetail())
                .setCustomerLegitimateInterestMarketingPermissions(customer.getCustomerLegitimateInterestMarketingPermissions())
                .setCustomerPhones(customer.getCustomerPhones())
                .setCustomerCreateUpdateWarnings(customer.getCustomerCreateUpdateWarnings());
    }

    public Customer createFullCustomerWithoutEmployments(Customer customer) {

        return new Customer(customer.getBrands(), customer.getCustomerNumber())
                .setCustomerBars(customer.getCustomerBars())
                .setCustomerAddress(customer.getCustomerAddress())
                .setCustomerEmails(customer.getCustomerEmails())
                .setCustomerIndividualDetail(customer.getCustomerIndividualDetail())
                .setCustomerLegitimateInterestMarketingPermissions(customer.getCustomerLegitimateInterestMarketingPermissions())
                .setCustomerPhones(customer.getCustomerPhones())
                .setCustomerCreateUpdateWarnings(customer.getCustomerCreateUpdateWarnings());
    }

    public Customer createFullCustomerWithoutCustomerPhones(Customer customer) {
        return new Customer(customer.getBrands(), customer.getCustomerNumber())
                .setCustomerBars(customer.getCustomerBars())
                .setCustomerAddress(customer.getCustomerAddress())
                .setCustomerEmails(customer.getCustomerEmails())
                .setCustomerIndividualDetail(customer.getCustomerIndividualDetail())
                .setCustomerLegitimateInterestMarketingPermissions(customer.getCustomerLegitimateInterestMarketingPermissions())
                .setCustomerCreateUpdateWarnings(customer.getCustomerCreateUpdateWarnings())
                .setCustomerEmployments(customer.getCustomerEmployments());
    }

    public Customer createFullCustomerWithMultipleCustomerPhones(Customer customer) {
        List<CustomerPhone> customerPhones = new CustomerRequestBuilder().createCustomerAddPhones(customer.getCustomerPhones());

        return new Customer(customer.getBrands(), customer.getCustomerNumber())
                .setCustomerBars(customer.getCustomerBars())
                .setCustomerAddress(customer.getCustomerAddress())
                .setCustomerEmails(customer.getCustomerEmails())
                .setCustomerPhones(customerPhones)
                .setCustomerIndividualDetail(customer.getCustomerIndividualDetail())
                .setCustomerLegitimateInterestMarketingPermissions(customer.getCustomerLegitimateInterestMarketingPermissions())
                .setCustomerCreateUpdateWarnings(customer.getCustomerCreateUpdateWarnings())
                .setCustomerEmployments(customer.getCustomerEmployments());
    }


    public Customer createFullCustomerWithMultipleCustomerEmployments(Customer customer) {
        List<CustomerEmployment> customerEmployments = new CustomerRequestBuilder().createCustomerAddEmployments(
                customer.getCustomerEmployments());

        return new Customer(customer.getBrands(), customer.getCustomerNumber())
                .setCustomerBars(customer.getCustomerBars())
                .setCustomerAddress(customer.getCustomerAddress())
                .setCustomerEmails(customer.getCustomerEmails())
                .setCustomerPhones(customer.getCustomerPhones())
                .setCustomerIndividualDetail(customer.getCustomerIndividualDetail())
                .setCustomerLegitimateInterestMarketingPermissions(customer.getCustomerLegitimateInterestMarketingPermissions())
                .setCustomerCreateUpdateWarnings(customer.getCustomerCreateUpdateWarnings())
                .setCustomerEmployments(customerEmployments);
    }


    public Customer createFullCustomerWithoutCustomerBars(Customer customer) {
        return new Customer(customer.getBrands(), customer.getCustomerNumber())
                .setCustomerAddress(customer.getCustomerAddress())
                .setCustomerEmails(customer.getCustomerEmails())
                .setCustomerIndividualDetail(customer.getCustomerIndividualDetail())
                .setCustomerLegitimateInterestMarketingPermissions(customer.getCustomerLegitimateInterestMarketingPermissions())
                .setCustomerCreateUpdateWarnings(customer.getCustomerCreateUpdateWarnings())
                .setCustomerPhones(customer.getCustomerPhones())
                .setCustomerEmployments(customer.getCustomerEmployments());
    }

    public Customer createFullCustomerWithoutBrands(Customer customer) {
        return new Customer(customer.getCustomerNumber())
                .setCustomerBars(customer.getCustomerBars())
                .setCustomerAddress(customer.getCustomerAddress())
                .setCustomerEmails(customer.getCustomerEmails())
                .setCustomerIndividualDetail(customer.getCustomerIndividualDetail())
                .setCustomerLegitimateInterestMarketingPermissions(customer.getCustomerLegitimateInterestMarketingPermissions())
                .setCustomerCreateUpdateWarnings(customer.getCustomerCreateUpdateWarnings())
                .setCustomerPhones(customer.getCustomerPhones())
                .setCustomerEmployments(customer.getCustomerEmployments());
    }

    public Customer createFullCustomerWithoutCustomerAddress(Customer customer) {
        return new Customer(customer.getBrands(), customer.getCustomerNumber())
                .setCustomerBars(customer.getCustomerBars())
                .setCustomerEmails(customer.getCustomerEmails())
                .setCustomerIndividualDetail(customer.getCustomerIndividualDetail())
                .setCustomerLegitimateInterestMarketingPermissions(customer.getCustomerLegitimateInterestMarketingPermissions())
                .setCustomerCreateUpdateWarnings(customer.getCustomerCreateUpdateWarnings())
                .setCustomerPhones(customer.getCustomerPhones())
                .setCustomerEmployments(customer.getCustomerEmployments());
    }

    public Customer createFullCustomerWithoutCustomerIndividualDetail(Customer customer) {
        return new Customer(customer.getBrands(), customer.getCustomerNumber())
                .setCustomerBars(customer.getCustomerBars())
                .setCustomerAddress(customer.getCustomerAddress())
                .setCustomerEmails(customer.getCustomerEmails())
                .setCustomerLegitimateInterestMarketingPermissions(customer.getCustomerLegitimateInterestMarketingPermissions())
                .setCustomerCreateUpdateWarnings(customer.getCustomerCreateUpdateWarnings())
                .setCustomerPhones(customer.getCustomerPhones())
                .setCustomerEmployments(customer.getCustomerEmployments());
    }

    public Customer createFullCustomerWithoutCustomerLegitimateInterestMarketingPermissions(Customer customer) {
        return new Customer(customer.getBrands(), customer.getCustomerNumber())
                .setCustomerBars(customer.getCustomerBars())
                .setCustomerAddress(customer.getCustomerAddress())
                .setCustomerIndividualDetail(customer.getCustomerIndividualDetail())
                .setCustomerEmails(customer.getCustomerEmails())
                .setCustomerCreateUpdateWarnings(customer.getCustomerCreateUpdateWarnings())
                .setCustomerPhones(customer.getCustomerPhones())
                .setCustomerEmployments(customer.getCustomerEmployments());
    }
}
