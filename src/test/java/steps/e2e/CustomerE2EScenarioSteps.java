package steps.e2e;

import api.model.LegitimateInterestBuilder;
import api.requests.CustomerClient;
import basic.BasicTest;
import io.cucumber.java.en.When;
import parsing.json.JsonParser;

public class CustomerE2EScenarioSteps extends BasicTest {

    //LI
    //BARs
    @When("there is a request to update customer with legitimate interest marketing permissions set to {string}")
    public void updateLIRequest(String operation) {

        if (operation.equalsIgnoreCase("TrueAll")) {
            customerLegitimateInterest = new LegitimateInterestBuilder().create(true).build();
        } else if (operation.equalsIgnoreCase("FalseAll")) {
            customerLegitimateInterest = new LegitimateInterestBuilder().create(false).build();
        } else if (operation.equalsIgnoreCase("ChannelNull")) {
            customerLegitimateInterest = new LegitimateInterestBuilder().create(true).withChannelValueSetTo(null).build();
        } else if (operation.equalsIgnoreCase("CanMarketNull")) {
            customerLegitimateInterest = new LegitimateInterestBuilder().create(true).setChannelsWithTheSameMarketValue(null).build();
        } else if (operation.equalsIgnoreCase("SourceNull")) {
            customerLegitimateInterest = new LegitimateInterestBuilder().create(true).setChannelsWithTheSameSourceValue(null).build();
        } else if (operation.equalsIgnoreCase("ChannelEmpty")) {
            customerLegitimateInterest = new LegitimateInterestBuilder().create(true).withChannelValueSetTo("").build();
        } else if (operation.equalsIgnoreCase("SourceEmpty")) {
            customerLegitimateInterest = new LegitimateInterestBuilder().create(true).setChannelsWithTheSameSourceValue("").build();
        } else if (operation.equalsIgnoreCase("EmailFalse")) {
            customerLegitimateInterest = new LegitimateInterestBuilder().create(true).setEmail(false).build();
        } else if (operation.equalsIgnoreCase("PostFalse")) {
            customerLegitimateInterest = new LegitimateInterestBuilder().create(true).setPost(false).build();
        } else if (operation.equalsIgnoreCase("PhoneFalse")) {
            customerLegitimateInterest = new LegitimateInterestBuilder().create(true).setPhone(false).build();
        } else if (operation.equalsIgnoreCase("SMSFalse")) {
            customerLegitimateInterest = new LegitimateInterestBuilder().create(true).setSms(false).build();
        }
        modifyCustomerLegitimateInterest = CustomerClient.modifyCustomerLegitimateInterestWithPooling(customerId.get(0), JsonParser.classToJsonString(customerLegitimateInterest));
    }


}
