package assertions;

import api.model.Customer;
import api.model.customernodes.*;
import com.esure.eis.V20.models.*;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.ResponseUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DxpCustomerAssertions extends CustomerCommonAssertions {

    AssertTest assertTest;

    public DxpCustomerAssertions(HttpResponse<JsonNode> response) {
        super(response);
    }

    public DxpCustomerAssertions compareMandatoryFieldsCustomerData(Customer requestData) {
        compareIndividualData(requestData.getCustomerIndividualDetail());
        compareAddress(requestData.getCustomerAddress());
        return this;
    }

    public DxpCustomerAssertions assertResponseResultsContainString(String expectedString) {
        assertTest.assertTest(response.getBody().toString().contains(expectedString), Matchers.is(true));
        return this;
    }

    public DxpCustomerAssertions compareIndividualData(CustomerIndividualDetail requestData) {
        JSONObject obj = getSubNode("individualDetails");
        assertTest.assertTest("First name", requestData.getFirstName(), Matchers.is(obj.get("firstName")));
        assertTest.assertTest("Last name", requestData.getLastName(), Matchers.is(obj.get("lastName")));
        assertTest.assertTest("Date of birth", requestData.getDateOfBirth(),
                Matchers.is(obj.get("dateOfBirth")));
        assertTest.assertTest("Marital status", requestData.getMaritalStatus(),
                Matchers.is(obj.get("maritalStatus")));
        assertTest.assertTest("Cars in household", requestData.getCarsInHousehold(),
                Matchers.is(obj.get("carsInHousehold")));
        assertTest.assertTest("Residency status", requestData.getResidencyStatus(),
                Matchers.is(obj.get("residencyStatus")));
        assertTest.assertTest("Number of children", requestData.getNumberOfChildren(),
                Matchers.is(obj.get("numberOfChildren")));

        return this;
    }

    public DxpCustomerAssertions compareAddress(CustomerAddress requestedData) {
        JSONArray obj = ResponseUtils.returnJsonArrayForKey(super.response, "addresses");
        var dxpAddress = new Gson().fromJson(obj.get(0).toString(),
                GenesisFacadeCustomerAddress.class);

        assertTest.assertTest("Sub Premises", requestedData.getSubPremises(), Matchers.is(dxpAddress.getSubPremises()));
        assertTest.assertTest("Postcode", requestedData.getPostcode(), Matchers.is(dxpAddress.getPostcode()));
        assertTest.assertTest("Street", requestedData.getStreet(), Matchers.is(dxpAddress.getStreet()));
        assertTest.assertTest("House Number", requestedData.getHouseNumber(), Matchers.is(dxpAddress.getHouseNumber()));
        assertTest.assertTest("House Name", requestedData.getHouseName(), Matchers.is(dxpAddress.getHouseName()));
        assertTest.assertTest("Locality", requestedData.getLocality(), Matchers.is(dxpAddress.getLocality()));
        assertTest.assertTest("County", requestedData.getCounty(), Matchers.is(dxpAddress.getCounty()));
        assertTest.assertTest("Country", requestedData.getCountry(), Matchers.is(dxpAddress.getCountry()));
        assertTest.assertTest("Postal Town", requestedData.getPostalTown(), Matchers.is(dxpAddress.getPostalTown()));

        return this;
    }

    public DxpCustomerAssertions compareEmployment(List<CustomerEmployment> customerEmployments) {
        JSONArray jsonArray = ResponseUtils.returnJsonArrayForKey(super.response, "customerEmployments");
        List<JSONObject> dxpCustomerEmployments = jsonArrayToList(jsonArray);

        customerEmployments.forEach(customerEmployment -> {
            var matchingCustomerEmployments = dxpCustomerEmployments.stream()
                    .findAny()
                    .filter(dxpCustomerEmployment -> dxpCustomerEmployment.getString("primaryOccupationCode").equals(customerEmployment.getPrimaryOccupation()))
                    .orElseThrow(() -> new AssertionError("No DXP employments found matching: " + customerEmployment.getPrimaryOccupation()));

            var dxpEmployment = new Gson().fromJson(
                    matchingCustomerEmployments.toString(),
                    GenesisFacadeAgentCustomerEmploymentInfo.class);

            assertTest.assertTest("Primary Occupation", customerEmployment.getPrimaryOccupation(),
                    Matchers.is(dxpEmployment.getPrimaryOccupationCode()));
            assertTest.assertTest("Primary Industry", customerEmployment.getPrimaryIndustry(),
                    Matchers.is(dxpEmployment.getPrimaryOccupationIndustry()));
            assertTest.assertTest("Secondary Occupation", customerEmployment.getSecondaryOccupation(),
                    Matchers.is(dxpEmployment.getSecondaryOccupation()));
            assertTest.assertTest("Secondary Industry", customerEmployment.getSecondaryIndustry(),
                    Matchers.is(dxpEmployment.getSecondaryIndustry()));
            assertTest.assertTest("Employment Status", customerEmployment.getEmploymentStatus(),
                    Matchers.is(dxpEmployment.getEmploymentStatus()));
        });

        return this;
    }

    public DxpCustomerAssertions compareEmails(List<CustomerEmail> emails) {
        JSONArray jsonArray = ResponseUtils.returnJsonArrayForKey(super.response, "emails");
        List<JSONObject> dxpEmails = jsonArrayToList(jsonArray);

        emails.forEach(email -> {
            var matchingEmail = dxpEmails.stream()
                    .findAny()
                    .filter(dxpEmail -> dxpEmail.getString("email").equals(email.getEmail()))
                    .orElseThrow(() -> new AssertionError("No DXP email found matching: " + email.getEmail()));

            var dxpEmail = new Gson().fromJson(matchingEmail.toString(),
                    GenesisFacadeCustomerEmail.class);

            assertTest.assertTest("Email", email.getEmail(), Matchers.is(dxpEmail.getEmail()));
            assertTest.assertTest("Email Confirmed", email.getEmailConfirmed(), Matchers.is(dxpEmail.isEmailConfirmed()));
            assertTest.assertTest("Id", email.getId(), Matchers.is(dxpEmail.getId()));
        });


        return this;
    }

    public DxpCustomerAssertions comparePhone(List<CustomerPhone> phones) {
        JSONArray jsonArray = ResponseUtils.returnJsonArrayForKey(super.response, "phones");
        List<JSONObject> dxpPhones = jsonArrayToList(jsonArray);

        phones.forEach(phone -> {
            var matchingPhone = dxpPhones.stream()
                    .findAny()
                    .filter(dxpPhone -> dxpPhone.getString("phoneNumber").equals(phone.getPhoneNumber()))
                    .orElseThrow(() -> new AssertionError("No DXP phone found matching: " + phone.getPhoneNumber()));

            var dxpPhone = new Gson().fromJson(matchingPhone.toString(),
                    GenesisFacadeCustomerPhone.class);

            assertTest.assertTest("Phone Type", phone.getPhoneType(), Matchers.is(dxpPhone.getPhoneType()));
            assertTest.assertTest("Phone Confirmed", phone.getPhoneConfirmed(), Matchers.is(dxpPhone.isPhoneConfirmed()));
            assertTest.assertTest("Phone Number", phone.getPhoneNumber(), Matchers.is(dxpPhone.getPhoneNumber()));
            assertTest.assertTest("Country Code", phone.getCountryCode(), Matchers.is(dxpPhone.getCountryCode()));
        });

        return this;
    }

    public DxpCustomerAssertions compareLegitimateInterest(
            List<LegitimateInterestMarketingObject> liPermissions) {
        JSONArray jsonArray = ResponseUtils.returnJsonArrayForKey(super.response, "legitimateInterestMarketingPermissions");
        List<JSONObject> dxpLegitimateInterests = jsonArrayToList(jsonArray);

        liPermissions.forEach(liPermission -> {
            var matchingLegitimateInterests = dxpLegitimateInterests.stream()
                    .findAny()
                    .filter(dxpLegitimateInterest -> dxpLegitimateInterest.getString("id").equals(liPermission.getId()))
                    .orElseThrow(() -> new AssertionError("No DXP legitimate interest found matching: " + liPermission.getId()));


            var dxpPermission = new Gson().fromJson(
                    matchingLegitimateInterests.toString(), GenesisFacadeLegitimateInterestMarketingPermission.class);

            assertTest.assertTest(liPermission.getBrand(),
                    Matchers.equalTo(dxpPermission.getBrand()));
            comparePermissions(liPermission.getCustomerMarketingPermissions(),
                    dxpPermission.getPermissions());
        });
        return this;
    }


    private JSONObject getSubNode(String subNodeKey) {
        return (JSONObject) ResponseUtils.returnResponseResultJSONObject(
                super.response).get(subNodeKey);
    }

    private void comparePermissions(List<CustomerMarketingPermission> requestPermissions,
                                    List<GenesisFacadeMarketingPermission> dxpPermissions) {

        requestPermissions.stream().forEach((customerMarketingPermission -> {
            compareWithDxpValue(customerMarketingPermission, dxpPermissions);
        }));
    }

    private void compareWithDxpValue(CustomerMarketingPermission permission,
                                     List<GenesisFacadeMarketingPermission> dxpPermission) {

        dxpPermission.stream().forEach((GenesisFacadeMarketingPermission perm) -> {
            if (perm.getChannel().equals(permission.getChannel())) {
                assertPermissionsValues(permission, perm);
            }
        });
    }

    private void assertPermissionsValues(CustomerMarketingPermission customerPermission,
                                         GenesisFacadeMarketingPermission dxpPermission) {

        assertTest.assertTest("Channel", customerPermission.getChannel(), Matchers.is(dxpPermission.getChannel()));
        assertTest.assertTest("Can Market", customerPermission.getCanMarket(),
                Matchers.is(dxpPermission.isCanMarket()));
        assertTest.assertTest("Source", customerPermission.getSource(), Matchers.is(dxpPermission.getSource()));
    }

    private List<JSONObject> jsonArrayToList(JSONArray jsonArray) {
        return IntStream.range(0, jsonArray.length())
                .mapToObj(jsonArray::getJSONObject)
                .collect(Collectors.toList());
    }

}
