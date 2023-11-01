package api.requests.help;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import exceptions.http.HttpRequestFailedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.Map;


@Slf4j
public class JsonRequest {

    private JsonRequest() {
    }

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String PUT = "PUT";

    public static HttpResponse<JsonNode> post(String url, String body) {
        try {
            HttpResponse<JsonNode> result = Unirest.post(url).header(CONTENT_TYPE, APPLICATION_JSON).body(body).asJson();
            displayRequestInfos(POST, url, body, result, "", null, null);
            return result;
        } catch (UnirestException var3) {
            throw new HttpRequestFailedException(url, var3);
        }
    }

    public static HttpResponse<JsonNode> postWithBasicAuth(String url, String body, String username, String password) {
        try {
            HttpResponse<JsonNode> result = Unirest.post(url).header(CONTENT_TYPE, APPLICATION_JSON).basicAuth(username, password).body(body).asJson();
            displayRequestInfos(POST, url, body, result, username + "/" + password, null, null);
            return result;
        } catch (UnirestException var5) {
            throw new HttpRequestFailedException(url, var5);
        }
    }

    public static HttpResponse<JsonNode> postWithBasicAuthAndHeaders(String url, Map<String, String> headers, String body, String username, String password) {
        try {
            HttpResponse<JsonNode> result = Unirest.post(url).header(CONTENT_TYPE, APPLICATION_JSON).headers(headers).basicAuth(username, password).body(body).asJson();
            displayRequestInfos(POST, url, body, result, username + "/" + password, headers, null);
            return result;
        } catch (UnirestException var6) {
            throw new HttpRequestFailedException(url, var6);
        }
    }

    public static HttpResponse<JsonNode> postWithNoBodyAndHeaders(String url, Map<String, String> headers) {
        try {
            HttpResponse<JsonNode> result = Unirest.post(url).header(CONTENT_TYPE, APPLICATION_JSON).headers(headers).asJson();
            displayRequestInfos(POST, url, "", result, "", headers, null);
            return result;
        } catch (UnirestException var3) {
            throw new HttpRequestFailedException(url, var3);
        }
    }

    public static HttpResponse<JsonNode> postWithBodyAndHeaders(String url, Map<String, String> headers, String body) {
        try {
            HttpResponse<JsonNode> result = Unirest.post(url).header(CONTENT_TYPE, APPLICATION_JSON).headers(headers).body(body).asJson();
            displayRequestInfos(POST, url, body, result, "", headers, null);
            return result;
        } catch (UnirestException var4) {
            throw new HttpRequestFailedException(url, var4);
        }
    }

    public static HttpResponse<JsonNode> postWithNoBodyBasicAuthAndHeaders(String url, Map<String, String> headers, String username, String password) {
        try {
            HttpResponse<JsonNode> result = Unirest.post(url).header(CONTENT_TYPE, APPLICATION_JSON).headers(headers).basicAuth(username, password).asJson();
            displayRequestInfos(POST, url, "", result, username + "/" + password, headers, null);
            return result;
        } catch (UnirestException var5) {
            throw new HttpRequestFailedException(url, var5);
        }
    }

    public static HttpResponse<JsonNode> put(String url, String body) {
        try {
            HttpResponse<JsonNode> result = Unirest.put(url).header(CONTENT_TYPE, APPLICATION_JSON).body(body).asJson();
            displayRequestInfos(PUT, url, body, result, "", null, null);
            return result;
        } catch (UnirestException var3) {
            throw new HttpRequestFailedException(url, var3);
        }
    }

    public static HttpResponse<JsonNode> putWithBodyAndHeaders(String url, Map<String, String> headers, String body) {
        try {
            HttpResponse<JsonNode> result = Unirest.put(url).header(CONTENT_TYPE, APPLICATION_JSON).headers(headers).body(body).asJson();
            displayRequestInfos(PUT, url, body, result, "", headers, null);
            return result;
        } catch (UnirestException var4) {
            throw new HttpRequestFailedException(url, var4);
        }
    }

    public static HttpResponse<JsonNode> put(String url) {
        try {
            HttpResponse<JsonNode> result = Unirest.put(url).header(CONTENT_TYPE, APPLICATION_JSON).asJson();
            displayRequestInfos(PUT, url, "", result, "", null, null);
            return result;
        } catch (UnirestException var2) {
            throw new HttpRequestFailedException(url, var2);
        }
    }

    public static HttpResponse<JsonNode> putWithBasicAuth(String url, String body, String user, String pass) {
        try {
            HttpResponse<JsonNode> result = Unirest.put(url).header(CONTENT_TYPE, APPLICATION_JSON).basicAuth(user, pass).body(body).asJson();
            displayRequestInfos(PUT, url, body, result, user + "/" + pass, null, null);
            return result;
        } catch (UnirestException var5) {
            throw new HttpRequestFailedException(url, var5);
        }
    }

    public static HttpResponse<JsonNode> get(String url) {
        try {
            HttpResponse<JsonNode> result = Unirest.get(url).asJson();
            displayRequestInfos(GET, url, "", result, "", null, null);
            return result;
        } catch (UnirestException var2) {
            throw new HttpRequestFailedException(url, var2);
        }
    }

    public static HttpResponse<JsonNode> getWithParams(String url, Map<String, Object> params) {
        try {
            HttpResponse<JsonNode> result = Unirest.get(url).queryString(params).asJson();
            displayRequestInfos(GET, url, "", result, "", null, params);
            return result;
        } catch (UnirestException var3) {
            throw new HttpRequestFailedException(url, var3);
        }
    }

    public static HttpResponse<JsonNode> getWithParamsAndHeaders(String url, Map<String, String> headers, Map<String, Object> params) {
        try {
            HttpResponse<JsonNode> result = Unirest.get(url).headers(headers).queryString(params).asJson();
            displayRequestInfos(GET, url, "", result, "", headers, params);
            return result;
        } catch (UnirestException var4) {
            throw new HttpRequestFailedException(url, var4);
        }
    }

    public static HttpResponse<JsonNode> getWithBasicAuthAndHeaders(String url, Map<String, String> headers, String username, String password) {
        try {
            HttpResponse<JsonNode> result = Unirest.get(url).headers(headers).basicAuth(username, password).asJson();
            displayRequestInfos(GET, url, "", result, username + "/" + password, headers, null);
            return result;
        } catch (UnirestException var5) {
            throw new HttpRequestFailedException(url, var5);
        }
    }

    public static HttpResponse<JsonNode> getWithBasicAuth(String url, String username, String password) {
        try {
            HttpResponse<JsonNode> result = Unirest.get(url).basicAuth(username, password).asJson();
            displayRequestInfos(GET, url, "", result, username + "/" + password, null, null);
            return result;
        } catch (UnirestException var4) {
            throw new HttpRequestFailedException(url, var4);
        }
    }

    public static HttpResponse<JsonNode> delete(String url) {
        try {
            HttpResponse<JsonNode> result = Unirest.delete(url).asJson();
            displayRequestInfos("DELETE", url, "", result, "", null, null);
            return result;
        } catch (UnirestException var2) {
            throw new HttpRequestFailedException(url, var2);
        }
    }

    public static void displayRequestInfos(String method, String url, String body, HttpResponse<JsonNode> result, String auth,
                                           Map<String, String> headers, Map<String, Object> params) {
        log.info("---");
        log.info("Execute request: " + method);
        log.info("URI: " + url);
        if (StringUtils.isNotEmpty(auth)) {
            log.info("Auth: {} ", auth);
        }
        if (headers != null && !headers.isEmpty()) {
            log.info("Headers: {} ", headers);
        }
        if (params != null && !params.isEmpty()) {
            log.info("Params: {} ", params);
        }

        log.info("Body: " + body);
        log.info("Status: " + result.getStatus());
        log.info("Response Body => " + result.getBody());
        log.info("---");
    }
}
