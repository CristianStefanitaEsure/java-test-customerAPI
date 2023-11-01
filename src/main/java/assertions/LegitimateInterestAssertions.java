package assertions;

import api.model.Customer;
import api.model.customernodes.CustomerMarketingPermission;
import api.model.customernodes.LegitimateInterestMarketingObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.hamcrest.Matchers;
import utils.ResponseUtils;

import java.util.ArrayList;
import java.util.List;

public class LegitimateInterestAssertions extends CustomerCommonAssertions {

    AssertTest assertTest;

    private final String LEGITIMATE_STRING = "legitimateInterestMarketingPermissions";
    private List<CustomerMarketingPermission> permissionsList = new ArrayList<>();

    public LegitimateInterestAssertions(
            HttpResponse<JsonNode> jsonResponse) {
        super(jsonResponse);
    }

    public LegitimateInterestAssertions compareValuesWithRequest(
            List<LegitimateInterestMarketingObject> requestPermissions) {

        comparePermissionValues(
                requestPermissions.get(0).getCustomerMarketingPermissions()
                        .get(0), permissionsList.get(0));

        comparePermissionValues(
                requestPermissions.get(0).getCustomerMarketingPermissions()
                        .get(2), permissionsList.get(1));

        comparePermissionValues(
                requestPermissions.get(0).getCustomerMarketingPermissions()
                        .get(1), permissionsList.get(2));

        comparePermissionValues(
                requestPermissions.get(0).getCustomerMarketingPermissions()
                        .get(3), permissionsList.get(3));
        return this;
    }

    public LegitimateInterestAssertions extractPermissionsMap() {
        Customer customer = ResponseUtils.parseResponseToCustomer(response);

        permissionsList = customer.getCustomerLegitimateInterestMarketingPermissions().get(0)
                .getCustomerMarketingPermissions();
        System.out.println("response data:" + permissionsList);
        return this;
    }

    private void comparePermissionValues(CustomerMarketingPermission requestValue,
                                         CustomerMarketingPermission responseValue) {
        //assertTest.assertTest(requestValue.getChannel(), Matchers.equalTo(responseValue.getChannel()));
        assertTest.assertTest(requestValue.getSource(), Matchers.equalTo(responseValue.getSource()));
        assertTest.assertTest(requestValue.getCanMarket(), Matchers.equalTo(responseValue.getCanMarket()));
    }

    public LegitimateInterestAssertions marketingPreferencesExistingStructure() {
        marketingPreferencesContains("Post", "DEFAULT", true);
        marketingPreferencesContains("Phone", "DEFAULT", true);
        marketingPreferencesContains("Post", "DEFAULT", true);
        marketingPreferencesContains("SMS", "DEFAULT", true);

        return this;
    }

    public LegitimateInterestAssertions marketingPreferencesContains(String expectedChannel, String expectedSource, boolean expectedCanMarket) {
        permissionsList.stream()
                .filter(marketingPreference -> marketingPreference.getChannel().equals(expectedChannel)
                        && (marketingPreference.getSource().equals(expectedSource))
                        && marketingPreference.getCanMarket().equals(expectedCanMarket))
                .findAny().orElseThrow(() -> new AssertionError("Marketing Preferences were not updated correctly in EIS customer: \n" +
                        "Expected: Channel: %s, Source: %s, CanMarket: %s\n" +
                        "Found: " + permissionsList));
        return this;
    }
}
