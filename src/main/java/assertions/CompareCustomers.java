package assertions;

import api.model.Customer;
import api.model.customernodes.*;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;

import java.util.List;


@Slf4j
public class CompareCustomers {

    static AssertTest assertTest;


    //TODO add validation for bars when tickets are completed in Sprint-24, Customer Legitimate interest covered as separate validation
    public static void assertCustomerResponseIsTheSame(Customer request, Customer response) {

        checkIndividualDetails(request.getCustomerIndividualDetail(),
                response.getCustomerIndividualDetail());
        checkCustomerEmails(request.getCustomerEmails(), response.getCustomerEmails());
        checkAddress(request.getCustomerAddress(), response.getCustomerAddress());
        checkCustomerEmployment(request.getCustomerEmployments(),
                response.getCustomerEmployments());
        checkBrands(request.getBrands(), response.getBrands());
        checkPhones(request.getCustomerPhones(), response.getCustomerPhones());
    }

    public static void assertMinimalCustomerResponseIsTheSame(Customer request, Customer response) {
        checkIndividualDetails(request.getCustomerIndividualDetail(),
                response.getCustomerIndividualDetail());
        checkAddress(request.getCustomerAddress(), response.getCustomerAddress());
        checkBrands(request.getBrands(), response.getBrands());
    }

    public static void checkIndividualDetails(CustomerIndividualDetail requestDetails,
                                              CustomerIndividualDetail responseDetails) {
        log.info("checkIndividualDetails: \n {}, \nvs \n{}", requestDetails, responseDetails);
        assertTest.assertTest(requestDetails.getFirstName(), Matchers.equalTo(responseDetails.getFirstName()));
        assertTest.assertTest(requestDetails.getLastName(), Matchers.equalTo(responseDetails.getLastName()));
        assertTest.assertTest(requestDetails.getCarsInHousehold(),
                Matchers.equalTo(responseDetails.getCarsInHousehold()));
        assertTest.assertTest(requestDetails.getDateOfBirth(),
                Matchers.equalTo(requestDetails.getDateOfBirth()));
        assertTest.assertTest(requestDetails.getMaritalStatus(), Matchers.equalTo(
                responseDetails.getMaritalStatus()));
        assertTest.assertTest(requestDetails.getNumberOfChildren(), Matchers.equalTo(
                responseDetails.getNumberOfChildren()));
        assertTest.assertTest(requestDetails.getResidencyStatus(),
                Matchers.equalTo(responseDetails.getResidencyStatus()));
        assertTest.assertTest(requestDetails.getTitle(), Matchers.equalTo(responseDetails.getTitle()));

    }

    public static void checkCustomerEmails(List<CustomerEmail> requestDetails,
                                           List<CustomerEmail> responseDetails) {

        assertTest.assertTest(requestDetails.get(0).getEmail(), Matchers.equalToIgnoringCase(responseDetails.get(0).getEmail()));

        assertTest.assertTest(requestDetails.get(0).getEmailConfirmed(), Matchers.equalTo(responseDetails.get(0).getEmailConfirmed()));
    }

    public static void checkCustomerNumber(String requestCN, String responseCN) {
        assertTest.assertTest(requestCN.equals(responseCN), Matchers.is(true));
    }

    public static void checkAddress(CustomerAddress requestAddress,
                                    CustomerAddress responseAddress) {
        assertTest.assertTest(requestAddress.getCountry(), Matchers.equalTo(responseAddress.getCountry()));
        ;
        assertTest.assertTest(requestAddress.getCounty(), Matchers.equalTo(responseAddress.getCounty()));
        assertTest.assertTest(requestAddress.getHouseName(), Matchers.equalTo(responseAddress.getHouseName()));
        assertTest.assertTest(requestAddress.getHouseNumber(),
                Matchers.equalTo(responseAddress.getHouseNumber()));
        assertTest.assertTest(requestAddress.getLocality(), Matchers.equalTo(responseAddress.getLocality()));
        assertTest.assertTest(requestAddress.getPostalTown(),
                Matchers.equalTo(responseAddress.getPostalTown()));
        assertTest.assertTest(requestAddress.getStreet(), Matchers.equalTo(responseAddress.getStreet()));
        assertTest.assertTest(requestAddress.getPostcode(), Matchers.equalTo(responseAddress.getPostcode()));
        assertTest.assertTest(requestAddress.getSubPremises(),
                Matchers.equalTo(responseAddress.getSubPremises()));
    }

    public static void checkCustomerEmployment(List<CustomerEmployment> employmentRequest,
                                               List<CustomerEmployment> employmentResponse) {
        assertTest.assertTest(employmentRequest.get(0).getEmploymentStatus(),
                Matchers.equalTo(employmentResponse.get(0).getEmploymentStatus()));
        ;
        assertTest.assertTest(employmentRequest.get(0).getPrimaryIndustry(), Matchers.equalTo(
                employmentRequest.get(0).getPrimaryIndustry()));
        assertTest.assertTest(employmentRequest.get(0).getPrimaryOccupation(),
                Matchers.equalTo(employmentResponse.get(0).getPrimaryOccupation()));
        assertTest.assertTest(employmentRequest.get(0).getSecondaryIndustry(),
                Matchers.equalTo(employmentResponse.get(0).getSecondaryIndustry()));
        assertTest.assertTest(employmentRequest.get(0).getSecondaryOccupation(),
                Matchers.equalTo(employmentResponse.get(0).getSecondaryOccupation()));
    }

    public static void checkBrands(List<String> brandsRequest, List<String> brandsResponse) {
        assertTest.assertTest(brandsRequest.equals(brandsResponse), Matchers.equalTo(true));
    }

    public static void checkPhones(List<CustomerPhone> requestPhones,
                                   List<CustomerPhone> responsePhones) {
        assertTest.assertTest(requestPhones.get(0).getPhoneNumber(),
                Matchers.equalTo(responsePhones.get(0).getPhoneNumber()));
        assertTest.assertTest(requestPhones.get(0).getPhoneConfirmed(),
                Matchers.equalTo(responsePhones.get(0).getPhoneConfirmed()));
        assertTest.assertTest(requestPhones.get(0).getPhoneType(),
                Matchers.equalTo(responsePhones.get(0).getPhoneType()));
        assertTest.assertTest(requestPhones.get(0).getCountryCode(),
                Matchers.equalTo(responsePhones.get(0).getCountryCode()));
    }
}
