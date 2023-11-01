package basic;

import api.model.Customer;
import api.model.customernodes.CustomerBar;
import api.model.customernodes.LegitimateInterestMarketingObject;
import api.requests.BodyFactory;
import assertions.AssertTest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import io.cucumber.java.After;

import java.util.ArrayList;
import java.util.List;

public class BasicTest {

    public static Customer customerResponse;
    public static Customer updatedCustomer;

    public AssertTest assertTest;

    public static HttpResponse<JsonNode> httpResponse;
    public static ArrayList<String> customerId = new ArrayList<>();
    public static ArrayList<String> customerEmail = new ArrayList<>();

    public static Customer findOrCreatecustomerResponse;
    public HttpResponse<JsonNode> findOrCreateHttpResponse;
    public static ArrayList<String> findOrCreateCustomerId = new ArrayList<>();
    public static ArrayList<String> findOrCreateCustomerEmail = new ArrayList<>();

    public BodyFactory bodyFactory = new BodyFactory();
    public Customer customer;

    public static Customer customerRequest;

    //get customer by customerID response
    public static Customer customerGetByCustomerIDResponse;
    public static HttpResponse<JsonNode> httpGetByCustomerIDResponse;

    //get customer by email response
    public static HttpResponse<JsonNode> httpGetCustomerByEmailResponse;

    //get customer's Email by email CustomerID
    public static HttpResponse<JsonNode> httpGetEmailByCustomerIDResponse;

    //get customerID by search API
    public static HttpResponse<JsonNode> httpGetCustomerIDBySearchAPIResponse;

    //Update Customer's Email Response
    public static HttpResponse<JsonNode> updateEmailResponse;

    //Update Bars
    public static HttpResponse<JsonNode> updateBarsResponse;
    public static List<CustomerBar> bars = null;
    public static List<CustomerBar> secondbars = null;
    public static List<CustomerBar> barsFromResponse = null;
    public static HttpResponse<JsonNode> customerBarResponse = null;

    //update LI
    public static List<LegitimateInterestMarketingObject> customerLegitimateInterest = null;
    public static HttpResponse<JsonNode> modifyCustomerLegitimateInterest;

    //Update Customer's Details
    public static HttpResponse<JsonNode> modifyCustomerDetailsResponse = null;


}
