package api.requests;

import api.model.Customer;
import java.util.concurrent.atomic.AtomicBoolean;
import utils.ResponseUtils;

public class CustomerAtomics {

    public static AtomicBoolean findCustomerBarsPresent(Customer customerBody) {
        AtomicBoolean returnedValue = new AtomicBoolean();

        returnedValue.set(
            ResponseUtils.parseResponseToCustomer(CustomerClient.findOrCreate(customerBody))
                .getCustomerBars().isEmpty()
        );

        return returnedValue;
    }

}
