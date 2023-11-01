package api.model;

import api.model.customernodes.*;
import com.github.javafaker.Faker;
import lombok.Getter;
import testdata.CustomerDataHolder;
import utils.FileLoader;
import utils.StringUtils;
import utils.constants.CustomerBuilderData;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CustomerRequestBuilder extends Customer {

    private Customer customerRequest;

    public CustomerRequestBuilder() {
        this.customerRequest = new Customer();
    }

    public CustomerRequestBuilder create(String filename) {
        customerRequest = FileLoader.asClass("json/" + filename, Customer.class);

        return this;
    }

    public CustomerRequestBuilder createFullCustomerWithAllFields() {
        customerRequest.setBrands(CustomerBuilderData.CUSTOMER_BRANDS)
                .setCustomerAddress(createCustomerAddress())
                .setCustomerEmails(createCustomerEmail())
                .setCustomerEmployments(createEmployments())
                .setCustomerIndividualDetail(createIndividualDetail())
                .setCustomerPhones(createCustomerPhones());

        return this;
    }

    public CustomerRequestBuilder createCustomerWithRequiredInfo() {
        customerRequest.setBrands(CustomerBuilderData.CUSTOMER_BRANDS)
                .setCustomerAddress(createCustomerAddress())
                .setCustomerIndividualDetail(createIndividualDetail());

        return this;
    }


    public CustomerRequestBuilder create(Customer existingCustomer) {
        customerRequest = existingCustomer;

        return this;
    }

    public CustomerRequestBuilder setUniqueFirstAndLastName() {
        customerRequest.getCustomerIndividualDetail()
                .setFirstName(CustomerDataHolder.getFirstName());
        customerRequest.getCustomerIndividualDetail().setLastName(CustomerDataHolder.getSurname());

        return this;
    }

    public CustomerRequestBuilder setCustomerFirstNameToValue(String firstName) {
        customerRequest.getCustomerIndividualDetail().setFirstName(firstName);

        return this;
    }

    public CustomerRequestBuilder setCustomerEmailToValue(String email) {
        List<CustomerEmail> customerEmails = customerRequest.getCustomerEmails();
        try {
            if (customerEmails.size() > 0) {
                customerEmails.get(0).setEmail(email);
            } else {
                CustomerEmail customerEmail = new CustomerEmail();
                customerEmail.setEmail(email);
                customerEmails.add(customerEmail);
            }
        } catch (Exception e) {
        }

        return this;
    }

    public CustomerRequestBuilder setCustomerLastNameToValue(String lastName) {
        customerRequest.getCustomerIndividualDetail().setLastName(lastName);

        return this;
    }

    public CustomerRequestBuilder setCustomerPostcodeToValue(String postcode) {
        customerRequest.getCustomerAddress().setPostcode(postcode);

        return this;
    }

    public CustomerRequestBuilder setDateOfBirthToValue(String dateOfBirth) {
        customerRequest.getCustomerIndividualDetail().setDateOfBirth(dateOfBirth);

        return this;
    }

    public CustomerRequestBuilder setCustomerEmailDataToValue(CustomerEmail email) {
        customerRequest.getCustomerEmails().get(0)
                .setEmailConfirmed(email.getEmailConfirmed()).setEmail(email.getEmail()).setId(email.getId());

        return this;
    }

    public CustomerRequestBuilder setCustomerNumber(String customerId) {
        customerRequest.setCustomerNumber(customerId);

        return this;
    }

    public CustomerRequestBuilder setUniqueHouseName() {
        customerRequest.getCustomerAddress().setHouseName(CustomerDataHolder.getHouseName());
        return this;
    }

    public CustomerRequestBuilder setUniqueHouseNumber() {
        customerRequest.getCustomerAddress().setHouseNumber(CustomerDataHolder.getHouseNumber());

        return this;
    }

    public CustomerRequestBuilder setEmptyHouseNameAndHouseNumber() {
        customerRequest.getCustomerAddress().setHouseName("");
        customerRequest.getCustomerAddress().setHouseNumber("");

        return this;
    }

    public CustomerRequestBuilder setBrands(List<String> brands) {
        customerRequest.setBrands(brands);

        return this;
    }

    public CustomerRequestBuilder setBars() {
        customerRequest.getCustomerBars()
                .add(FileLoader.asClass("json/bar.json", CustomerBar.class));

        return this;
    }

    public CustomerRequestBuilder setRemovedBars() {
        customerRequest.getCustomerBars()
                .set(0, FileLoader.asClass("json/barRemoved.json", CustomerBar.class));

        return this;
    }

    public Customer build() {
        return customerRequest;
    }


    private CustomerAddress createCustomerAddress() {
        var customerAddress = new CustomerAddress();

        customerAddress.setHouseName(CustomerBuilderData.HOUSE_NAME)
                .setHouseNumber(CustomerBuilderData.HOUSE_NUMBER)
                .setCountry(CustomerBuilderData.COUNTRY)
                .setPostcode(CustomerBuilderData.POST_CODE)
                .setCounty(CustomerBuilderData.COUNTY)
                .setLocality(CustomerBuilderData.LOCALITY)
                .setPostalTown(CustomerBuilderData.POSTAL_TOWN)
                .setStreet(CustomerBuilderData.STREET)
                .setPostalTown(CustomerBuilderData.POSTAL_TOWN)
                .setSubPremises(CustomerBuilderData.SUB_PREMISES);

        return customerAddress;
    }

    private List<CustomerBar> createCustomerBars() {
        var bars = new CustomerBar();
        bars.setBarCode(CustomerBuilderData.BAR_CODE)
                .setBarType(CustomerBuilderData.BAR_TYPE)
                .setCategory(CustomerBuilderData.BAR_CATEGORY)
                .setDescription(CustomerBuilderData.BAR_DESCRIPTION)
                .setReason(CustomerBuilderData.BAR_REASON)
                .setPolicyNumber("");

        return List.of(bars);
    }

    private List<CustomerEmail> createCustomerEmail() {
        var customerEmail = new CustomerEmail();

        customerEmail.setEmail(StringUtils.generateRandomEmail())
                .setEmailConfirmed(CustomerBuilderData.CUSTOMER_EMAIL_CONFIRMATION);

        return List.of(customerEmail);

    }

    public List<CustomerEmployment> createEmployments() {
        var employment = new CustomerEmployment();
        employment.setEmploymentStatus(CustomerBuilderData.CUSTOMER_EMPLOYMENT_STATUS)
                .setPrimaryIndustry(CustomerBuilderData.CUSTOMER_EMPLOYMENT_INDUSTRY)
                .setPrimaryOccupation(CustomerBuilderData.CUSTOMER_EMPLOYMENT_PRIMARY_OCCUPATION)
                .setSecondaryOccupation(CustomerBuilderData.CUSTOMER_EMPLOYMENT_SECONDARY_OCCUPATION)
                .setSecondaryIndustry(CustomerBuilderData.CUSTOMER_EMPLOYMENT_SECONDARY_INDUSTRY);

        return List.of(employment);

    }
    public List<CustomerEmployment> createCustomerAddEmployments(List<CustomerEmployment> initialEmployments) {
        List<CustomerEmployment> customerEmployments = new ArrayList<>();
        customerEmployments.addAll(initialEmployments);
        customerEmployments.addAll(new CustomerRequestBuilder().createEmployments());
        return customerEmployments;
    }

    private CustomerIndividualDetail createIndividualDetail() {
        var individualDetail = new CustomerIndividualDetail();

        individualDetail.setLastName(CustomerBuilderData.CUSTOMER_INDIVIDUAL_LASTNAME)
                .setFirstName(CustomerBuilderData.CUSTOMER_INDIVIDUAL_FIRST_NAME)
                .setDateOfBirth(CustomerBuilderData.CUSTOMER_INDIVIDUAL_DOB)
                .setGenderCd(CustomerBuilderData.CUSTOMER_INDIVIDUAL_GENDER)
                .setMaritalStatus(CustomerBuilderData.CUSTOMER_INDIVIDUAL_STATUS)
                .setCarsInHousehold(CustomerBuilderData.CUSTOMER_INDIVIDUAL_CARS_IN_HOUSEHOLD)
                .setNumberOfChildren(CustomerBuilderData.CUSTOMER_INDIVIDUAL_NUMBERS_OF_CHILDREN)
                .setResidencyStatus(CustomerBuilderData.CUSTOMER_INDIVIDUAL_RESIDENCY_STATUS)
                .setTitle(CustomerBuilderData.CUSTOMER_INDIVIDUAL_TITLE);
        return individualDetail;
    }

    public List<CustomerPhone> createCustomerPhones() {
        var customerPhone = CustomerPhone.builder()
                .phoneNumber(new Faker().number().digits(11))
                .countryCode(CustomerBuilderData.CUSTOMER_COUNTRY_CODE)
                .phoneType(CustomerBuilderData.CUSTOMER_PHONE_TYPE)
                .phoneConfirmed(CustomerBuilderData.CUSTOMER_PHONE_CONFIRMED)
                .build();

        return List.of(customerPhone);
    }

    public List<CustomerPhone> createCustomerAddPhones(List<CustomerPhone> initialPhones) {
        List<CustomerPhone> customerPhones = new ArrayList<>();
        customerPhones.addAll(initialPhones);
        customerPhones.addAll(new CustomerRequestBuilder().createCustomerPhones());
        return customerPhones;
    }


    private CustomerMarketingPermission createMarketingPermission() {
        var marketPermission = new CustomerMarketingPermission();
        marketPermission.setCanMarket(CustomerBuilderData.CUSTOMER_MARKET_PERMISSION)
                .setChannel(CustomerBuilderData.CUSTOMER_CHANNEL)
                .setSource(CustomerBuilderData.CUSTOMER_SOURCE);

        return marketPermission;
    }

}
