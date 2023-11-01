package api.requests;

import api.model.Customer;
import api.model.CustomerSearch;
import api.model.customernodes.CustomerBar;
import api.model.customernodes.CustomerEmail;
import api.requests.help.JsonRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import exceptions.http.HttpRequestFailedException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import parsing.json.JsonParser;
import testdata.StringRepository;
import utils.EndpointsConfigurationData;
import utils.ResponseUtils;

import java.time.Duration;
import java.util.List;

import static org.awaitility.Awaitility.given;
import static parsing.json.JsonParser.classToJsonString;
import static utils.EndpointsConfigurationData.*;

@Slf4j
public class CustomerClient {

    public static HttpResponse<JsonNode> customerResponse;
    public static ObjectMapper mapper = new ObjectMapper();
    private static final String POST_CUSTOMER_URL = CUSTOMER_API_URL + CUSTOMERS_PATH;

    public static HttpResponse<JsonNode> createCustomer(Customer body) {
        try {
            return JsonRequest.postWithBasicAuth(POST_CUSTOMER_URL, classToJsonString(body),
                    EndpointsConfigurationData.USER, EndpointsConfigurationData.PASS);
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage() + " Cause " + e.getCause());
            throw new HttpRequestFailedException(
                    POST_CUSTOMER_URL, e);
        }
    }

    public static HttpResponse<JsonNode> getCustomerByCustomerNumber(String customerNumber) {
        try {
            return JsonRequest.getWithBasicAuth(POST_CUSTOMER_URL + "/" + customerNumber,
                    EndpointsConfigurationData.USER, EndpointsConfigurationData.PASS);
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage() + " Cause " + e.getCause());
            throw new HttpRequestFailedException(
                    POST_CUSTOMER_URL + "/" + customerNumber, e);
        }
    }

    public static HttpResponse<JsonNode> updateAnExistingCustomerWithGivenDetails(Customer body) {
        try {
            return JsonRequest.putWithBasicAuth(CUSTOMER_API_URL + CUSTOMERS_PATH + "/" + body.getCustomerNumber(),
                    JsonParser.classToJsonString(body),
                    EndpointsConfigurationData.USER, EndpointsConfigurationData.PASS
            );

        } catch (Exception e) {
            throw new HttpRequestFailedException(
                    CUSTOMER_API_URL + CUSTOMERS_PATH + "/" + body.getCustomerNumber(), e);
        }
    }

    public static HttpResponse<JsonNode> putCustomerEmailData(String customerId,
                                                              CustomerEmail body) {
        var requestUri =
                CUSTOMER_API_URL + CUSTOMERS_PATH + "/" + customerId + EMAILS_SEARCH_BY_CUSTOMER_ID
                        + "/" + body.getId();
        try {
            return JsonRequest.put(requestUri, JsonParser.classToJsonString(body));

        } catch (Exception e) {
            log.error("Exception: " + e.getMessage() + " Cause " + e.getCause());
            throw new HttpRequestFailedException(
                    CUSTOMER_API_URL + CUSTOMERS_PATH + FIND_OR_CREATE, e);
        }
    }

    public static HttpResponse<JsonNode> findOrCreate(Customer body) {
        try {
            customerResponse = JsonRequest.postWithBasicAuth(CUSTOMER_API_URL + CUSTOMERS_PATH + FIND_OR_CREATE,
                    classToJsonString(body), EndpointsConfigurationData.USER, EndpointsConfigurationData.PASS);
            return customerResponse;
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage() + " Cause " + e.getCause());
            throw new HttpRequestFailedException(
                    CUSTOMER_API_URL + CUSTOMERS_PATH + FIND_OR_CREATE, e);
        }
    }

    public static HttpResponse<JsonNode> findOrCreateWithFlag(Customer body) {
        try {
            customerResponse = JsonRequest.post(
                    CUSTOMER_API_URL + CUSTOMERS_PATH + FIND_OR_CREATE + "?isPCWQuoteRequest=true",
                    JsonParser.classToJsonString(body));
            return customerResponse;
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage() + " Cause " + e.getCause());
            throw new HttpRequestFailedException(
                    CUSTOMER_API_URL + CUSTOMERS_PATH + FIND_OR_CREATE, e);
        }
    }

    public static HttpResponse<JsonNode> postWithPooling(String url, Customer body) {
        try {
            given()
                    .ignoreException(JSONException.class)
                    .await()
                    .atLeast(Duration.ofSeconds(5))
                    .pollDelay(Duration.ofSeconds(5))
                    .atMost(Duration.ofSeconds(20))
                    .pollInterval(Duration.ofSeconds(1))
                    .until(() -> JsonRequest.post(url, JsonParser.classToJsonString(body
                    )).getStatus() == 200);

            return customerResponse;
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage() + " Cause " + e.getCause());
            throw new HttpRequestFailedException("Exception", e);
        }
    }

    public static HttpResponse<JsonNode> findOrCreateWithPooling(Customer body) {
        try {
            given()
                    .ignoreException(JSONException.class)
                    .await()
                    .atLeast(Duration.ofSeconds(5))
                    .pollDelay(Duration.ofSeconds(5))
                    .atMost(Duration.ofSeconds(20))
                    .pollInterval(Duration.ofSeconds(1))
                    .until(() -> findOrCreate(body).getStatus() == 200);

            return customerResponse;
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage() + " Cause " + e.getCause());
            throw new HttpRequestFailedException("Exception", e);
        }
    }

    public static HttpResponse<JsonNode> findOrCreateWithPoolingAndFlag(Customer body) {
        try {
            given()
                    .ignoreException(JSONException.class)
                    .await()
                    .atLeast(Duration.ofSeconds(5))
                    .pollDelay(Duration.ofSeconds(5))
                    .atMost(Duration.ofSeconds(20))
                    .pollInterval(Duration.ofSeconds(1))
                    .until(() -> findOrCreateWithFlag(body).getStatus() == 200);

            return customerResponse;
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage() + " Cause " + e.getCause());
            throw new HttpRequestFailedException("Exception", e);
        }
    }

    public static HttpResponse<JsonNode> searchForACustomerNumberByExactMatch(CustomerSearch body) {
        try {
            return JsonRequest.postWithBasicAuth(
                    CUSTOMER_API_URL + CUSTOMERS_PATH + CUSTOMER_SEARCH,
                    classToJsonString(body), EndpointsConfigurationData.USER, EndpointsConfigurationData.PASS);
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage() + " Cause " + e.getCause());
            throw new HttpRequestFailedException(
                    CUSTOMER_API_URL + CUSTOMERS_PATH + CUSTOMER_SEARCH, e);
        }
    }

    public static HttpResponse<JsonNode> getCustomerByEmail(String emailAddress) {
        try {
            given()
                    .ignoreException(JSONException.class)
                    .await()
                    .atLeast(Duration.ofSeconds(5))
                    .pollDelay(Duration.ofSeconds(5))
                    .atMost(Duration.ofSeconds(50))
                    .pollInterval(Duration.ofSeconds(1))
                    .until(() -> {
                        ResponseUtils.returnResponseResult(JsonRequest.get(
                                CUSTOMER_API_URL + CUSTOMERS_PATH + BY_EMAIL_SEARCH + emailAddress));
                        return true;
                    });

            return JsonRequest.get(CUSTOMER_API_URL + CUSTOMERS_PATH + BY_EMAIL_SEARCH + emailAddress);
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage() + " Cause " + e.getCause());
            throw new HttpRequestFailedException(
                    CUSTOMER_API_URL + CUSTOMERS_PATH + BY_EMAIL_SEARCH + emailAddress, e);
        }
    }

    public static HttpResponse<JsonNode> getEmailByCustomerNumber(String customerId) {
        var requestUrl = CUSTOMER_API_URL + CUSTOMERS_PATH + "/" + customerId + EMAILS_SEARCH_BY_CUSTOMER_ID;

        try {
            given()
                    .ignoreException(JSONException.class)
                    .await()
                    .atLeast(Duration.ofSeconds(5))
                    .pollDelay(Duration.ofSeconds(5))
                    .atMost(Duration.ofSeconds(10))
                    .pollInterval(Duration.ofSeconds(1))
                    .until(() -> JsonRequest.get(requestUrl).getStatus() == 200
                    );

            return JsonRequest.get(requestUrl);
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage() + " Cause" + e.getCause());
            throw new HttpRequestFailedException(requestUrl, e);
        }
    }

    public static HttpResponse<JsonNode> modifyCustomerLegitimateInterestWithPooling(
            String customerId,
            String body) {

        var requestUrl =
                CUSTOMER_API_URL + CUSTOMERS_PATH + "/" + customerId + LEGITIMATE_INTEREST;

        try {
            given()
                    .ignoreException(JSONException.class)
                    .await()
                    .atLeast(Duration.ofSeconds(5))
                    .pollDelay(Duration.ofSeconds(5))
                    .atMost(Duration.ofSeconds(20))
                    .pollInterval(Duration.ofSeconds(1))
                    .until(() -> !JsonRequest.put(
                                    requestUrl, body).getBody().toString()
                            .contains(StringRepository.LEGITIMATE_INTEREST_ENDPOINT_ERROR));

            return JsonRequest.put(
                    requestUrl, body);

        } catch (Exception e) {
            log.error("Exception: " + e.getMessage() + "Cause" + e.getCause());
            throw new HttpRequestFailedException(requestUrl, e);
        }
    }

    public static void sendPutBarsRequest(String customerId,
                                          List<CustomerBar> bars) {
        HttpResponse<JsonNode> response;
        var requestUri =
                CUSTOMER_API_URL + CUSTOMERS_PATH + "/" + customerId + CUSTOMER_BARS;

        try {
            given().ignoreException(JSONException.class)
                    .await()
                    .atLeast(Duration.ofSeconds(5))
                    .pollDelay(Duration.ofSeconds(5))
                    .atMost(Duration.ofSeconds(20))
                    .pollInterval(Duration.ofSeconds(1))
                    .until(() ->
                            JsonRequest.put(requestUri, JsonParser.classToJsonString(bars)).getStatus()
                                    == 200);

        } catch (Exception e) {
            log.error("Exception: " + e.getMessage() + " Cause " + e.getCause());
            throw new HttpRequestFailedException(
                    CUSTOMER_API_URL + CUSTOMERS_PATH + "/" + customerId + CUSTOMER_BARS, e);
        }
    }

    public static HttpResponse<JsonNode> searchCustomerUntilSuccessOrTimeout(
            CustomerSearch requestBody) {
        try {
            given()
                    .ignoreException(JSONException.class)
                    .await()
                    .atLeast(Duration.ofSeconds(5))
                    .pollDelay(Duration.ofSeconds(5))
                    .atMost(Duration.ofSeconds(30))
                    .pollInterval(Duration.ofSeconds(1))
                    .until(() -> {
                        ResponseUtils
                                .returnResponseResult(searchForACustomerNumberByExactMatch(requestBody));
                        return true;
                    });
            return searchForACustomerNumberByExactMatch(requestBody);

        } catch (Exception e) {
            log.error("Exception: " + e.getMessage() + " Cause " + e.getCause());
            throw new HttpRequestFailedException("Exception", e);
        }
    }
}
