package assertions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.diff.JsonDiff;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import io.swagger.util.Json;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import utils.ResponseUtils;

@Getter
@Slf4j
public abstract class CustomerCommonAssertions<T> {

    protected HttpResponse<JsonNode> response;

    public CustomerCommonAssertions(HttpResponse<JsonNode> response) {
        this.response = response;
    }

    AssertTest assertTest;

    public T assertThatResponseReceivedExpectedCode(int expectedCode) {
        assertTest.assertTest("assertThatResponseReceivedExpectedCode", response.getStatus(),
                Matchers.is(Matchers.equalTo(expectedCode)));

        return (T) this;
    }

    public T assertThatResponseIsEqualToRequest(String requestBody) {
        log.info("assertThatResponseIsEqualToRequest: {}", requestBody);
        com.fasterxml.jackson.databind.JsonNode request = null;
        com.fasterxml.jackson.databind.JsonNode response = null;
        try {
            request = Json.mapper().readTree(requestBody);
            response = Json.mapper().readTree(ResponseUtils.returnResponseResult(this.response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        final com.fasterxml.jackson.databind.JsonNode patchNode = JsonDiff.asJson(request,
                response);
        assertTest.assertTest(patchNode.isEmpty(), Matchers.is(true));
        return (T) this;
    }

    public T assertErrorMessageValueContent(String expectedMessage) {
        log.info("assertErrorMessageValueContent: {}", expectedMessage);
        assertTest.assertTest(response.getBody().toString().contains(expectedMessage),
                Matchers.equalTo(true));

        return (T) this;
    }

    public T assertThatResponseHasDataInResults(String requiredText) {
        log.info("assertThatResponseHasDataInResults: {}", requiredText);
        assertTest.assertTest(ResponseUtils.returnResponseResult(response),
                Matchers.containsString(requiredText));

        return (T) this;
    }

    public T assertValueIsPresentInObject(String key, Object value) {
        log.info("assertValueIsPresentInObject: key={} value={}", key, value);
        assertTest.assertTest(response.getBody().getObject().get(key)
                , Matchers.equalTo(value));

        return (T) this;
    }

    public T assertThatResponseIsEmpty() {
        log.info("assertThatResponseIsEmpty");
        assertTest.assertTest(response.getBody().getObject().getJSONArray("results").toString().contains("[]"),
                Matchers.is(true));

        return (T) this;
    }

    public T assertThatResponseContainsErrors(String errorMessage) {
        log.info("assertThatResponseContainsErrors: {}", errorMessage);
        assertTest.assertTest(response.getBody().toString(), Matchers.containsString(errorMessage));

        return (T) this;
    }

    public T assertMessageValueContentNull(String key) {
        log.info("assertMessageValueContentNull: key={}", key);
        assertTest.assertTest(response.getBody().getObject().get(key)
                , Matchers.equalTo(null));

        return (T) this;
    }
}
