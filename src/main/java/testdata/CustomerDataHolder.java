package testdata;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

@Slf4j
public class CustomerDataHolder {

    private final static String NAME = "John";
    private final static String SURNAME = "Smith";
    private final static String HOUSE_NAME = "House";
    private static String generatedString;
    private static String firstName;
    private static String surname;
    private static String houseName;
    private static String houseNumber;

    public static void generateRandomDynamicTestData() {
        log.info("Generate random dynamic test data");
        generatedString = RandomStringUtils.random(20, true, false);
        firstName = NAME + generatedString;
        surname = SURNAME + generatedString;
        houseName = HOUSE_NAME + generatedString;
        houseNumber = RandomStringUtils.random(3, false, true);
    }

    public static String getHouseName() {
        return houseName;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static String getSurname() {
        return surname;
    }

    public static String getHouseNumber() {
        return houseNumber;
    }

}
