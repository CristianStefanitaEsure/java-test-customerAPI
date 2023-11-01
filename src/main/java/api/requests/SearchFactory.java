package api.requests;

import api.model.Customer;
import api.model.CustomerSearch;
import utils.FileLoader;

public class SearchFactory {

    public static CustomerSearch create(JsonType input) {
        return FileLoader
            .asClass(input.json(), CustomerSearch.class);
    }

}
