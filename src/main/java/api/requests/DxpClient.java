package api.requests;

import api.requests.help.JsonRequest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import httpclient.dxp.DXPJsonRequest;
import utils.EndpointsConfigurationData;
import utils.EnvironmentsNew;

public class DxpClient {

    private static final String GET_CUSTOMER_URL =
            EndpointsConfigurationData.DXP_ENV_URL + EndpointsConfigurationData.DXP_GET_CUSTOMER;

    public static HttpResponse<JsonNode> getCustomer(String customerId) {
        HttpResponse<JsonNode> response;
        EnvironmentsNew env = EnvironmentsNew.getCurrentEnv();

        if (env == EnvironmentsNew.DEV || env == EnvironmentsNew.DEV07) {
            response = JsonRequest.getWithBasicAuth(GET_CUSTOMER_URL + customerId,
                    EndpointsConfigurationData.USER, EndpointsConfigurationData.PASS);
        } else {
            response = DXPJsonRequest.getWithBasicAuth(GET_CUSTOMER_URL + customerId);
        }

        return response;
    }

}
