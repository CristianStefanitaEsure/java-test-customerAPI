package steps;

import basic.BasicTest;
import io.cucumber.java.After;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CleanAfterSteps extends BasicTest {

    @After
    public void removeArraylist() {
        log.info("Clear the test data...");
        if (customerId != null && !customerId.isEmpty()) {
            customerId.clear();
        }
        if (customerEmail != null && !customerEmail.isEmpty()) {
            customerEmail.clear();
        }
        if (findOrCreateCustomerId != null && !findOrCreateCustomerId.isEmpty()) {
            findOrCreateCustomerId.clear();
        }
        if (findOrCreateCustomerEmail != null && !findOrCreateCustomerEmail.isEmpty()) {
            findOrCreateCustomerEmail.clear();
        }
        updateEmailResponse = null;
        modifyCustomerDetailsResponse = null;
        updatedCustomer = null;
        customerResponse = null;
        customerRequest = null;
        httpResponse = null;
        findOrCreatecustomerResponse = null;
        findOrCreateHttpResponse = null;
        bodyFactory = null;
        customer = null;
        customerGetByCustomerIDResponse = null;
        httpGetByCustomerIDResponse = null;
        httpGetCustomerByEmailResponse = null;
        httpGetEmailByCustomerIDResponse = null;
        httpGetCustomerIDBySearchAPIResponse = null;
        updateBarsResponse = null;
        customerBarResponse = null;
        if (bars != null && !bars.isEmpty()) {
            bars.clear();
        }
        if (secondbars != null && !secondbars.isEmpty()) {
            secondbars.clear();
        }
        if (barsFromResponse != null && !barsFromResponse.isEmpty()) {
            barsFromResponse.clear();
        }
        if (customerLegitimateInterest != null && !customerLegitimateInterest.isEmpty()) {
            customerLegitimateInterest.clear();
        }
        modifyCustomerLegitimateInterest = null;
        modifyCustomerDetailsResponse = null;
    }

}