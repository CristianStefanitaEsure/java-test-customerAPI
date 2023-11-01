package utils;

import api.model.Customer;
import api.model.CustomerResponse;
import api.model.customernodes.CustomerEmail;
import api.model.customernodes.CustomerPhone;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

@Slf4j
public class ResponseUtils {

    public static String extractCustomerNumber(HttpResponse<JsonNode> response) {
        CustomerResponse customerResponse = new Gson().fromJson(response.getBody().toString(),
                CustomerResponse.class);
        return customerResponse.getResults().get(0).getCustomerNumber();
    }

    public static List<CustomerEmail> extractCustomerEmails(HttpResponse<JsonNode> response) {
        CustomerResponse customerResponse = new Gson().fromJson(response.getBody().toString(),
                CustomerResponse.class);
        return customerResponse.getResults().get(0).getCustomerEmails();
    }

    public static List<CustomerPhone> extractCustomerPhones(HttpResponse<JsonNode> response) {
        CustomerResponse customerResponse = new Gson().fromJson(response.getBody().toString(),
                CustomerResponse.class);
        return customerResponse.getResults().get(0).getCustomerPhones();
    }

    public static CustomerResponse parseCustomerResponse(HttpResponse<JsonNode> response) {
        return new Gson().fromJson(response.getBody().toString(),
                CustomerResponse.class);
    }

    public static String extractErrorSummary(HttpResponse<JsonNode> response) {
        CustomerResponse customerResponse = new Gson().fromJson(response.getBody().toString(),
                CustomerResponse.class);
        return customerResponse.getErrors().get(0).toString();
    }


    public static Customer parseResponseToCustomer(HttpResponse<JsonNode> response) {
        return new Gson().fromJson(returnResponseResult(response), Customer.class);
    }


    public static String returnResponseResult(HttpResponse<JsonNode> response) {
        return response.getBody().getObject().getJSONArray("results").get(0).toString();
    }

    public static JSONObject returnResponseResultJSONObject(HttpResponse<JsonNode> response) {
        return response.getBody().getObject();
    }

    public static JSONArray returnJsonArrayForKey(HttpResponse<JsonNode> response, String key) {
        return (JSONArray) response.getBody().getObject().get(key);
    }

    public static String printer(Object object, ObjectMapper mapper) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            log.info("Exception: " + e.getMessage() + " Cause " + e.getCause());
            return null;
        }
    }

}
