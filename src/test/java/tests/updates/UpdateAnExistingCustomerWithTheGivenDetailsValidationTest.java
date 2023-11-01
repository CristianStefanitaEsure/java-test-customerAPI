package tests.updates;

import api.requests.BodyFactory;
import api.requests.CustomerClient;
import assertions.CustomerAssertions;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testdata.CustomerDataHolder;
import testdata.StringRepository;

import static logging.Steps.*;

@Slf4j
public class UpdateAnExistingCustomerWithTheGivenDetailsValidationTest {
    private BodyFactory bodyFactory = new BodyFactory();

    @BeforeEach
    public void init() {
        CustomerDataHolder.generateRandomDynamicTestData();
    }

    @Test
    public void modifyCustomerWithIncorrectData() {
        given("there is an existing customer in the system");
        HttpResponse<JsonNode> createResponse = CustomerClient.createCustomer(
                bodyFactory.minimumFieldsCustomer());

        when("a user sends PUT request to modify the customer with incorrect name");
        CustomerAssertions customerAssertions = new CustomerAssertions(
                CustomerClient.updateAnExistingCustomerWithGivenDetails(bodyFactory.fullModifiedCustomerIncorrectNames(createResponse)));

        then("the customer data will not be modified in the process");
        customerAssertions.assertResponseReceivedExpectedCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        customerAssertions.assertErrorMessageValueContent(
                StringRepository.INCORRECT_MODIFY_REQUEST);
    }
}
