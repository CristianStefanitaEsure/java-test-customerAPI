package assertions;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;


@Slf4j
public class AssertTest {

    public static <T> void assertTest(String reason, T actual, Matcher<? super T> matcher) {
        Description description = new StringDescription();
        description.appendText(reason).appendText(" - \nExpected: ").appendDescriptionOf(matcher).appendText("\n  found: ");
        matcher.describeMismatch(actual, description);

        log.info(description.toString());

        if (!matcher.matches(actual)) {
            throw new AssertionError(description.toString());
        }
    }

    public static <T> void assertTest(T actual, Matcher<? super T> matcher) {
        Description description = new StringDescription();
        description.appendText(" - \nExpected: ").appendDescriptionOf(matcher).appendText("\n  found: ");
        matcher.describeMismatch(actual, description);

        log.info(description.toString());

        if (!matcher.matches(actual)) {
            throw new AssertionError(description.toString());
        }
    }

}
