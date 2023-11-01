package assertions;

import api.model.customernodes.CustomerBar;
import com.mashape.unirest.http.HttpResponse;
import org.hamcrest.Matchers;
import utils.ResponseUtils;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public class CustomerBarsAssertions extends CustomerCommonAssertions {

    private final List<CustomerBar> actualBars;
    private CustomerBar expectedBar;
    static AssertTest assertTest;

    public CustomerBarsAssertions(HttpResponse actualResponse) {
        super(actualResponse);
        this.actualBars = ResponseUtils.parseResponseToCustomer(actualResponse).getCustomerBars();
    }

    public CustomerBarsAssertions assignRequestedBarForAssertion(CustomerBar expectedBar) {
        this.expectedBar = expectedBar;

        return this;
    }

    public CustomerBarsAssertions assertBarsDataIsCorrect(CustomerBar expectedBar) {
        Optional<CustomerBar> foundBar = Optional.empty();
        boolean isFound = false;
        for (CustomerBar actualBar : actualBars) {
            if (actualBar.getBarType().equals(expectedBar.getBarType()))
                if (actualBar.getCategory() == null) {
                    if (actualBar.getPolicyNumber().equals(expectedBar.getPolicyNumber())) {
                        isFound = true;
                    }
                } else {
                    if (actualBar.getCategory().equals(expectedBar.getCategory()) && (actualBar.getPolicyNumber().equals(expectedBar.getPolicyNumber()))) {
                        isFound = true;
                    }
                }
            if (isFound) {
                foundBar = Optional.of(actualBar);
                break;
            }
        }

        assertTest.assertTest("PolicyNumber", foundBar.get().getPolicyNumber(), Matchers.equalTo(expectedBar.getPolicyNumber()));
        assertTest.assertTest("Description", foundBar.get().getDescription(), Matchers.equalTo(expectedBar.getDescription()));
        assertTest.assertTest("BarType", foundBar.get().getBarType(), Matchers.equalTo(expectedBar.getBarType()));
        assertTest.assertTest("Category", foundBar.get().getCategory(), Matchers.equalTo(expectedBar.getCategory()));
        assertTest.assertTest("StartDate", foundBar.get().getStartDate(),
                Matchers.containsString(OffsetDateTime.now().toLocalDate().toString()));

        return this;
    }

    public CustomerBarsAssertions assertMinimalBarsDataIsCorrect(CustomerBar expectedBar) {
        var firstBar = actualBars.get(0);

        assertTest.assertTest(firstBar.getBarType(), Matchers.equalTo(expectedBar.getBarType()));
        assertTest.assertTest(firstBar.getStartDate(),
                Matchers.containsString(OffsetDateTime.now().toLocalDate().toString()));

        return this;
    }

    public CustomerBarsAssertions assertBarIdIsCorrect() {
        assertTest.assertTest(actualBars.get(0).getBarCode(), Matchers.equalTo(expectedBar.getBarCode()));

        return this;
    }

    public CustomerBarsAssertions assertMinimalBarFromList(CustomerBar expectedBar) {
        var matchingBar = actualBars.stream().findFirst()
                .filter((CustomerBar bar) -> bar.getBarType().equals(expectedBar.getBarType())).get();
        assertTest.assertTest(matchingBar.getBarType(), Matchers.equalTo(expectedBar.getBarType()));

        return this;
    }

    public CustomerBarsAssertions assertEndDateIsAsExpected(CustomerBar expectedBar) {
        assertTest.assertTest(actualBars.get(0).getEndDate(), Matchers.equalTo(expectedBar.getEndDate()));

        return this;
    }

    public CustomerBarsAssertions assertRemoveReasonIsAsExpected() {
        assertTest.assertTest(actualBars.get(0).getRemoveReason(),
                Matchers.equalTo(expectedBar.getRemoveReason()));

        return this;
    }

    public CustomerBarsAssertions assertRemoveOtherIsAsExpected() {
        assertTest.assertTest(actualBars.get(0).getRemoveOther(), Matchers.equalTo(expectedBar.getRemoveOther()));

        return this;
    }
}
