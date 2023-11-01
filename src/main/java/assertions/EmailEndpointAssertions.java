package assertions;

import api.model.customernodes.CustomerEmail;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import utils.ResponseUtils;

import java.util.List;

@Slf4j
public class EmailEndpointAssertions extends CustomerCommonAssertions {

    public EmailEndpointAssertions(HttpResponse response) {
        super(response);
    }

    AssertTest assertTest;

    public EmailEndpointAssertions assertEmailIsTheSameAsInRequest(CustomerEmail requestData) {
        log.info("assertEmailIsTheSameAsInRequest: " + requestData);
        CustomerEmail responseEmail = new Gson().fromJson(
                ResponseUtils.returnResponseResult(response),
                CustomerEmail.class);
        assertTest.assertTest(requestData.getEmail(), Matchers.equalTo(responseEmail.getEmail()));
        assertTest.assertTest(requestData.getEmailConfirmed(),
                Matchers.equalTo(responseEmail.getEmailConfirmed()));
        assertTest.assertTest(requestData.getId(),
                Matchers.equalTo(responseEmail.getId()));
        return this;
    }

    public EmailEndpointAssertions assertEmailIsEmpty(List requestData) {
        assertTest.assertTest("Assert email is empty", requestData.isEmpty(), Matchers.is(true));
        return this;
    }

}
