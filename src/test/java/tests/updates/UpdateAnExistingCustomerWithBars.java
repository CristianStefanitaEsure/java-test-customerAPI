package tests.updates;

import api.model.Customer;
import api.model.CustomerBarsBuilder;
import api.model.CustomerRequestBuilder;
import api.model.customernodes.CustomerBar;
import api.model.definedvalues.BarReason;
import api.requests.CustomerClient;
import assertions.CustomerBarsAssertions;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import testdata.CustomerDataHolder;
import utils.ResponseUtils;
import utils.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static logging.Steps.*;
import static utils.constants.CustomerBuilderData.generateRequiredCustomerData;

public class UpdateAnExistingCustomerWithBars {

    @BeforeEach
    public void init() {
        CustomerDataHolder.generateRandomDynamicTestData();
        generateRequiredCustomerData();
    }

    @Test
    public void addBarToACustomer() {
        Customer customer = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();

        given(" there is an existing customer");
        var customerId = ResponseUtils.extractCustomerNumber(
                CustomerClient.createCustomer(customer));

        when(" there is request to add bar to a customer");
        var bars = new CustomerBarsBuilder().createUWFullBar().build();
        CustomerClient.sendPutBarsRequest(customerId, bars);

        then(" the customer bars data will match requested data");
        CustomerBarsAssertions assertions = new CustomerBarsAssertions(
                CustomerClient.getCustomerByCustomerNumber(customerId));
        assertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
        assertions.assertBarsDataIsCorrect(bars.get(0));

    }

    @Test
    public void addMandatoryOnlyFieldsBarToACustomer() {
        var bars = new CustomerBarsBuilder().createMinimalBar().build();
        var customer = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();

        given(" there is an existing customer");
        {
            var customerId = ResponseUtils.extractCustomerNumber(
                    CustomerClient.createCustomer(customer));

            when(" there is a request to add bar to a customer with only mandatory fields");
            CustomerClient.sendPutBarsRequest(customerId, bars);

            then(" the customer bars data will batch requested data");
            CustomerBarsAssertions assertions = new CustomerBarsAssertions(
                    CustomerClient.getCustomerByCustomerNumber(customerId));
            assertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
            assertions.assertMinimalBarsDataIsCorrect(bars.get(0));
        }
    }

    @Test
    public void addSecondBarToACustomer() {
        var barOne = new CustomerBarsBuilder().createUWFullBar().build();
        var customer = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();

        given(" there is an existing customer with a bar");
        var customerId = ResponseUtils.extractCustomerNumber(
                CustomerClient.createCustomer(customer));
        CustomerClient.sendPutBarsRequest(customerId,
                barOne);

        when(" there is a request to add second bar to a customer");
        var secondBar = new CustomerBarsBuilder().createMinimalBar().build();
        CustomerClient.sendPutBarsRequest(customerId, secondBar);
        var secondBarAssertion = new CustomerBarsAssertions(
                CustomerClient.getCustomerByCustomerNumber(customerId));
        secondBarAssertion.assertMinimalBarFromList(secondBar.get(0));
    }

    @Test
    public void modifyCustomerBar() {
        var barOne = new CustomerBarsBuilder().createUWFullBar().build();
        var customer = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();

        given(" there is a customer with an existing bar");
        var customerId = ResponseUtils.extractCustomerNumber(
                CustomerClient.createCustomer(customer));
        CustomerClient.sendPutBarsRequest(customerId, barOne);

        when(" there is a request to update customerBar");
        var responseBar = ResponseUtils.parseResponseToCustomer(
                CustomerClient.getCustomerByCustomerNumber(customerId)).getCustomerBars();
        responseBar.get(0).setReason(BarReason.DISCR.toString());
        CustomerClient.sendPutBarsRequest(customerId, responseBar);

        then(" there will be bar with updated field in response for another request");
        var customerBarAssertion = new CustomerBarsAssertions(
                CustomerClient.getCustomerByCustomerNumber(customerId));

        customerBarAssertion.assignRequestedBarForAssertion(responseBar.get(0))
                .assertBarsDataIsCorrect(responseBar.get(0)).assertBarIdIsCorrect();
    }

    @Test
    public void deleteCustomerBar() {
        var bar = new CustomerBarsBuilder().createUWFullBar().build();
        bar.get(0).setStartDate(StringUtils.applyTimeFormatter(LocalDateTime.now().minusDays(10L)));
        var customer = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();

        given(" there is a customer with an existing bar");
        var customerId = ResponseUtils.extractCustomerNumber(
                CustomerClient.createCustomer(customer));
        CustomerClient.sendPutBarsRequest(customerId, bar);
        List<CustomerBar> customerBars = ResponseUtils.parseResponseToCustomer(
                CustomerClient.getCustomerByCustomerNumber(customerId)).getCustomerBars();

        then(" there can be a request to set end date on today or past to expire the bar");
        customerBars.get(0)
                .setEndDate(StringUtils.applyTimeFormatter(LocalDateTime.now().minusYears(2L)));
        customerBars.get(0).setRemoveOther("Removing due to reason");
        customerBars.get(0).setRemoveReason("UWB_ERROR");
        CustomerClient.sendPutBarsRequest(customerId, customerBars);

        and(" date will be set to required");
        CustomerBarsAssertions barsAssertions = new CustomerBarsAssertions(
                CustomerClient.getCustomerByCustomerNumber(customerId)).assignRequestedBarForAssertion(
                customerBars.get(0));
        barsAssertions.assertBarIdIsCorrect()
                .assertRemoveOtherIsAsExpected();

    }

    @ParameterizedTest
    @ValueSource(strings = {"ACC_FLG", "ACC_BAR", "VAL_INV", "FRD_BAR"})
    public void addAllNonUWBarToACustomer(String barType) {

        Customer customer = new CustomerRequestBuilder().createCustomerWithRequiredInfo().build();

        given(" there is an existing customer");
        var customerId = ResponseUtils.extractCustomerNumber(
                CustomerClient.createCustomer(customer));

        when(" there is request to add bar to a customer");
        List<CustomerBar> bars = null;
        if (barType.equalsIgnoreCase("ACC_FLG") || barType.equalsIgnoreCase("ACC_BAR")) {
            bars = new CustomerBarsBuilder().createNonUWBarsWithValue(barType).build();
            CustomerClient.sendPutBarsRequest(customerId, bars);

        } else if (barType.equalsIgnoreCase("VAL_INV") || barType.equalsIgnoreCase("FRD_BAR")) {
            bars = new CustomerBarsBuilder().createVAL_INVAndFRD_BARWithValue(barType).build();
            CustomerClient.sendPutBarsRequest(customerId, bars);
        }

        then(" the customer bars data will match requested data");
        CustomerBarsAssertions assertions = new CustomerBarsAssertions(
                CustomerClient.getCustomerByCustomerNumber(customerId));
        assertions.assertThatResponseReceivedExpectedCode(HttpStatus.SC_OK);
        assertions.assertBarsDataIsCorrect(bars.get(0));

    }


}
