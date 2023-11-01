package steps.customer.bar;

import api.model.CustomerBarsBuilder;
import api.model.definedvalues.BarReason;
import api.requests.CustomerClient;
import api.requests.help.JsonRequest;
import basic.BasicTest;
import io.cucumber.java.en.When;
import parsing.json.JsonParser;
import utils.ResponseUtils;
import utils.StringUtils;

import java.time.LocalDateTime;

import static utils.EndpointsConfigurationData.*;

public class CustomerBarSteps extends BasicTest {

    @When("there is request to {string} bar to a customer with {string}")
    public void sendPutBarsRequest(String operation, String bartype) {

        String url = CUSTOMER_API_URL + CUSTOMERS_PATH + "/" + customerId.get(0) + CUSTOMER_BARS;
        if (operation.equalsIgnoreCase("add")) {
            switch (bartype.toLowerCase()) {
                case "uwbar" -> {
                    bars = new CustomerBarsBuilder().createUWFullBar().build();
                    CustomerClient.sendPutBarsRequest(customerId.get(0), bars);
                }
                case "mandatoryfieldsonly" -> {
                    bars = new CustomerBarsBuilder().createMinimalBar().build();
                    CustomerClient.sendPutBarsRequest(customerId.get(0), bars);
                }
                case "secondbars" -> {
                    secondbars = new CustomerBarsBuilder().createMinimalBar().build();
                    CustomerClient.sendPutBarsRequest(customerId.get(0), secondbars);
                }
                case "acc_flg", "acc_bar" -> {
                    bars = new CustomerBarsBuilder().createNonUWBarsWithValue(bartype).build();
                    CustomerClient.sendPutBarsRequest(customerId.get(0), bars);
                }
                case "val_inv", "frd_bar" -> {
                    bars = new CustomerBarsBuilder().createVAL_INVAndFRD_BARWithValue(bartype).build();
                    CustomerClient.sendPutBarsRequest(customerId.get(0), bars);
                }
                case "uw_inv" -> {
                    bars = new CustomerBarsBuilder().createUW_INVithValue(bartype).build();
                    CustomerClient.sendPutBarsRequest(customerId.get(0), bars);
                }
                case "nullbartype" -> {
                    bars = new CustomerBarsBuilder().createMinimalBar().build();
                    bars.get(0).setBarType(null);
                    customerBarResponse = JsonRequest.put(url, JsonParser.classToJsonString(bars));
                }
                case "incorrectbartype" -> {
                    bars = new CustomerBarsBuilder().createMinimalBar().build();
                    bars.get(0).setBarType("");
                    customerBarResponse = JsonRequest.put(url, JsonParser.classToJsonString(bars));
                }
                case "uwbarwithoutcategory" -> {
                    bars = new CustomerBarsBuilder().createUWFullBar().build();
                    bars.get(0).setCategory(null);
                    customerBarResponse = JsonRequest.put(url, JsonParser.classToJsonString(bars));
                }
                case "uwbarwitincorrectcategory" -> {
                    bars = new CustomerBarsBuilder().createUWFullBar().build();
                    bars.get(0).setCategory("");
                    customerBarResponse = JsonRequest.put(url, JsonParser.classToJsonString(bars));
                }
                case "uwbarwithoutreason" -> {
                    bars = new CustomerBarsBuilder().createUWFullBar().build();
                    bars.get(0).setReason(null);
                    customerBarResponse = JsonRequest.put(url, JsonParser.classToJsonString(bars));
                }
                case "uwbarwitincorrectreason" -> {
                    bars = new CustomerBarsBuilder().createUWFullBar().build();
                    bars.get(0).setReason("");
                    customerBarResponse = JsonRequest.put(url, JsonParser.classToJsonString(bars));
                }
                case "policyvalidationbarwithoutcategory" -> {
                    bars = new CustomerBarsBuilder().createVAL_INVAndFRD_BARWithValue("VAL_INV").build();
                    bars.get(0).setCategory(null);
                    customerBarResponse = JsonRequest.put(url, JsonParser.classToJsonString(bars));
                }
                case "policyvalidationbarwitincorrectcategory" -> {
                    bars = new CustomerBarsBuilder().createVAL_INVAndFRD_BARWithValue("VAL_INV").build();
                    bars.get(0).setCategory("");
                    customerBarResponse = JsonRequest.put(url, JsonParser.classToJsonString(bars));
                }
                case "policyvalidationbarwithoutreason" -> {
                    bars = new CustomerBarsBuilder().createVAL_INVAndFRD_BARWithValue("VAL_INV").build();
                    bars.get(0).setReason(null);
                    customerBarResponse = JsonRequest.put(url, JsonParser.classToJsonString(bars));
                }
                case "policyvalidationbarwitincorrectreason" -> {
                    bars = new CustomerBarsBuilder().createVAL_INVAndFRD_BARWithValue("VAL_INV").build();
                    bars.get(0).setReason("");
                    customerBarResponse = JsonRequest.put(url, JsonParser.classToJsonString(bars));
                }
                case "fraudbarwithoutcategory" -> {
                    bars = new CustomerBarsBuilder().createVAL_INVAndFRD_BARWithValue("FRD_BAR").build();
                    bars.get(0).setCategory(null);
                    customerBarResponse = JsonRequest.put(url, JsonParser.classToJsonString(bars));
                }
                case "fraudbarwitincorrectcategory" -> {
                    bars = new CustomerBarsBuilder().createVAL_INVAndFRD_BARWithValue("FRD_BAR").build();
                    bars.get(0).setCategory("");
                    customerBarResponse = JsonRequest.put(url, JsonParser.classToJsonString(bars));
                }
                case "fraudbarwithoutreason" -> {
                    bars = new CustomerBarsBuilder().createVAL_INVAndFRD_BARWithValue("FRD_BAR").build();
                    bars.get(0).setReason(null);
                    customerBarResponse = JsonRequest.put(url, JsonParser.classToJsonString(bars));
                }
                case "fraudbarwitincorrectreason" -> {
                    bars = new CustomerBarsBuilder().createVAL_INVAndFRD_BARWithValue("FRD_BAR").build();
                    bars.get(0).setReason("");
                    customerBarResponse = JsonRequest.put(url, JsonParser.classToJsonString(bars));
                }
                case "accountflagwithcategory" -> {
                    bars = new CustomerBarsBuilder().createVAL_INVAndFRD_BARWithValue("ACC_FLG").build();
                    bars.get(0).setCategory(BarReason.OTHER.getCategory().toString());
                    customerBarResponse = JsonRequest.put(url, JsonParser.classToJsonString(bars));
                }
                case "accountflagwithreason" -> {
                    bars = new CustomerBarsBuilder().createVAL_INVAndFRD_BARWithValue("ACC_FLG").build();
                    bars.get(0).setReason(BarReason.OTHER.toString());
                    customerBarResponse = JsonRequest.put(url, JsonParser.classToJsonString(bars));
                }
                case "accountbarwithcategory" -> {
                    bars = new CustomerBarsBuilder().createVAL_INVAndFRD_BARWithValue("ACC_BAR").build();
                    bars.get(0).setCategory(BarReason.OTHER.getCategory().toString());
                    customerBarResponse = JsonRequest.put(url, JsonParser.classToJsonString(bars));
                }
                case "accountbarwithreason" -> {
                    bars = new CustomerBarsBuilder().createVAL_INVAndFRD_BARWithValue("ACC_BAR").build();
                    bars.get(0).setReason(BarReason.OTHER.toString());
                    customerBarResponse = JsonRequest.put(url, JsonParser.classToJsonString(bars));
                }
            }
        } else if (operation.equalsIgnoreCase("UpdateExisting")) {
            var paramter = bartype.split("=")[0];
            var value1 = bartype.split("=")[1];
            if (paramter.equalsIgnoreCase("reason")) {
                bars.get(0).setReason(value1);
            }
        }
    }


    @When("there is a request to remove Bars by setting end date to {long} years earlier")
    public void setBarEndDate(Long Value) {
        barsFromResponse = ResponseUtils.parseResponseToCustomer(CustomerClient.getCustomerByCustomerNumber(customerId.get(0))).getCustomerBars();

        barsFromResponse.get(0).setEndDate(StringUtils.applyTimeFormatter(LocalDateTime.now().minusYears(Value)));
        barsFromResponse.get(0).setRemoveOther("Removing due to reason");
        barsFromResponse.get(0).setRemoveReason("UWB_ERROR");
        CustomerClient.sendPutBarsRequest(customerId.get(0), barsFromResponse);
    }


}
