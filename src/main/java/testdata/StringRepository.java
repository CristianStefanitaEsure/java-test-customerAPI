package testdata;

public class StringRepository {

    public static final String MISSING_FIELD_ERROR = "Bad Request";
    public static final String INCORRECT_CUSTOMER_ID = "iAmCustomer";
    public static final String FAILED_GET_EMAIL = "Failed to retrieve customer emails";
    public static final String FAILED_GET_CUSTOMER = "Failed to retrieve customer";
    public static final String FAILED_TO_SEARCH_CUSTOMER = "Failed to search customers";
    public static final String INCORRECT_MODIFY_REQUEST = "Failed to update customer";
    public static final String INCORRECT_EMAIL_PUT_REQUEST = "Failed to update customer email";
    public static final String TOO_LONG_VALUE = "Attribute value longer than 50 symbols";
    public static final String LEGITIMATE_INTEREST_DXP_BRAND = "\"brand\":\"ES\"";
    public static final String LEGITIMATE_INTEREST_DXP_PERMISSIONS =
        "{\"channel\":\"Phone\",\"canMarket\":true,"
            + "\"source\":\"DEFAULT\"},{\"channel\":\"Post\",\"canMarket\":true,"
            + "\"source\":\"DEFAULT\"},{\"channel\":\"SMS\",\"canMarket\":true,"
            + "\"source\":\"DEFAULT\"},{\"channel\":\"Email\",\"canMarket\":true,\"source\":\"DEFAULT\"}";
    public static final String LEGITIMATE_INTEREST_ENDPOINT_ERROR = "Exception occurred in customer search (for identifying root id) due to the structure being returned from the DXP in an unexpected format.";

}
