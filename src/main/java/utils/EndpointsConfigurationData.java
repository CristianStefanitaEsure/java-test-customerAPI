package utils;

import static environments.Environment.DEBUG_ONLY_overrideDefaultEnvironment;
import static environments.Environment.getCurrentEnv;
import static utils.EnvironmentsNew.setup;

public class EndpointsConfigurationData {

    static {
        // setup("app06");
        //  setup("app07");
            DEBUG_ONLY_overrideDefaultEnvironment("staging", "staging");

        DXP_ENV_URL = EnvironmentsNew.getCurrentEnv() != null
                ? EnvironmentsNew.getCurrentEnv().getDxpV20BaseUrl()
                : getCurrentEnv().getDxpV20BaseUrl();

        CUSTOMER_API_URL = EnvironmentsNew.getCurrentEnv() != null
                ? EnvironmentsNew.getCurrentEnv().getEsureBaseUrl()
                : getCurrentEnv().getEsureBaseUrl();
    }

    /*
     * EIS API configuration data
     * */

    public static String DXP_ENV_URL;
    public static final String DXP_ADV_SEARCH = "backoffice-customer-common/v1/customers/search-advanced";
    public static final String DXP_GET_CUSTOMER = "agent-facade/v1/customers/";

    // new - changed model
    //public static final String DXP_GET_CUSTOMER = "backoffice-customer-individual/v1/individual-customers/";
    public static final String USER = "qa";
    public static final String PASS = "qa";

    /*
     * Esure API configuration data
     * */

    public static String CUSTOMER_API_URL;
    public static final String CUSTOMERS_PATH = "java-app-customer/v1/customers";
    public static final String CUSTOMER_SEARCH = "/search";
    public static final String BY_EMAIL_SEARCH = "/by-email/";
    public static final String EMAILS_SEARCH_BY_CUSTOMER_ID = "/emails";
    public static final String FIND_OR_CREATE = "/find-or-create";
    public static final String LEGITIMATE_INTEREST = "/legitimate-interest-marketing-permissions";
    public static final String CUSTOMER_BARS = "/customer-bars";

}
