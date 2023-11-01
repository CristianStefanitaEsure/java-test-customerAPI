package api.requests;

import api.model.Customer;
import utils.FileLoader;

public class CustomerFactory {

    public static Customer create(JsonType input) {
        return FileLoader
            .asClass(input.json(), Customer.class);
    }

}
