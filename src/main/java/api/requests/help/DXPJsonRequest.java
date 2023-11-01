package api.requests.help;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import exceptions.http.HttpRequestFailedException;

import java.util.Map;

import static api.requests.help.JsonRequest.*;

public class DXPJsonRequest {
    public DXPJsonRequest() {
    }

    public static HttpResponse<JsonNode> postWithBasicAuthAndHeaders(String url, Map<String, String> headers, String body) {
        DXPAuth dxpAuth = DXPAuth.getInstance();

        try {
            HttpResponse<JsonNode> result = Unirest.post(url).header("Content-Type", "application/json").headers(headers).basicAuth(dxpAuth.getUsername(), dxpAuth.getPassword()).body(body).asJson();
            displayRequestInfos(POST, url, body, result, dxpAuth.getUsername() + "/" + dxpAuth.getPassword(), headers, null);
            return result;
        } catch (UnirestException var5) {
            throw new HttpRequestFailedException(url, var5);
        }
    }

    public static HttpResponse<JsonNode> getWithBasicAuth(String url) {
        DXPAuth dxpAuth = DXPAuth.getInstance();

        try {
            HttpResponse<JsonNode> result = Unirest.get(url).basicAuth(dxpAuth.getUsername(), dxpAuth.getPassword()).asJson();
            displayRequestInfos(GET, url, null, result, dxpAuth.getUsername() + "/" + dxpAuth.getPassword(), null, null);
            return result;
        } catch (UnirestException var3) {
            throw new HttpRequestFailedException(url, var3);
        }
    }

    public static HttpResponse<JsonNode> getWithBasicAuthAndHeaders(String url, Map<String, String> headers) {
        DXPAuth dxpAuth = DXPAuth.getInstance();

        try {
            HttpResponse<JsonNode> result = Unirest.get(url).headers(headers).basicAuth(dxpAuth.getUsername(), dxpAuth.getPassword()).asJson();
            displayRequestInfos(GET, url, null, result, dxpAuth.getUsername() + "/" + dxpAuth.getPassword(), headers, null);
            return result;
        } catch (UnirestException var4) {
            throw new HttpRequestFailedException(url, var4);
        }
    }

    public static HttpResponse<JsonNode> putWithBasicAuth(String url, String body) {
        DXPAuth dxpAuth = DXPAuth.getInstance();

        try {
            HttpResponse<JsonNode> result = Unirest.put(url).basicAuth(dxpAuth.getUsername(), dxpAuth.getPassword()).body(body).asJson();
            displayRequestInfos(PUT, url, body, result, dxpAuth.getUsername() + "/" + dxpAuth.getPassword(), null, null);
            return result;
        } catch (UnirestException var4) {
            throw new HttpRequestFailedException(url, var4);
        }
    }
}
