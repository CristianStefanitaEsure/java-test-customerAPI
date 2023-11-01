package utils.constants;

import com.github.javafaker.Faker;
import utils.StringUtils;

import java.time.OffsetDateTime;
import java.util.List;

public class CustomerBuilderData {

    // Customer Address
    public static final List<String> CUSTOMER_BRANDS = List.of("ES");
    public static String HOUSE_NAME = new Faker().address().streetName();
    public static String HOUSE_NUMBER = new Faker().address().buildingNumber();
    public static final String COUNTRY = "GB";
    public static final String POST_CODE = "39202";
    public static final String COUNTY = "Shire";
    public static final String LOCALITY = "Small Shire Part";
    public static final String POSTAL_TOWN = "Bigger Shire Town";
    public static final String STREET = new Faker().address().streetName();
    public static final String CUSTOMER_TYPE = "INDV";
    public static final String SUB_PREMISES = new Faker().address().streetName();

    // Customer Bars
    public static final String BAR_CODE = new Faker().random().hex();
    public static final String BAR_TYPE = "UW_BAR";
    public static final String BAR_CATEGORY = "ON_SERV";
    public static final String BAR_DESCRIPTION = "BAR DESCRIPTION";
    public static final String BAR_REASON = "Some Reason";

    // Customer Email
    public static String CUSTOMER_UNIQUE_EMAIL = StringUtils.generateRandomEmail();
    public static final Boolean CUSTOMER_EMAIL_CONFIRMATION = Boolean.TRUE;

    // Customer Employment
    public static final String CUSTOMER_EMPLOYMENT_STATUS = "E";
    public static final String CUSTOMER_EMPLOYMENT_INDUSTRY = "906";
    public static final String CUSTOMER_EMPLOYMENT_PRIMARY_OCCUPATION = "A01";
    public static final String CUSTOMER_EMPLOYMENT_SECONDARY_OCCUPATION = "A01";
    public static final String CUSTOMER_EMPLOYMENT_SECONDARY_INDUSTRY = "224";

    //Customer Individual
    public static String CUSTOMER_INDIVIDUAL_LASTNAME = new Faker().name().lastName();
    public static String CUSTOMER_INDIVIDUAL_FIRST_NAME = new Faker().name().firstName();
    public static final String CUSTOMER_INDIVIDUAL_DOB = String.valueOf(
        OffsetDateTime.now().minusYears(30).toLocalDate());
    public static final String CUSTOMER_INDIVIDUAL_GENDER = "M";
    public static final String CUSTOMER_INDIVIDUAL_STATUS = "M";
    public static final String CUSTOMER_INDIVIDUAL_CARS_IN_HOUSEHOLD = "1";
    public static final String CUSTOMER_INDIVIDUAL_NUMBERS_OF_CHILDREN = "2";
    public static final String CUSTOMER_INDIVIDUAL_RESIDENCY_STATUS = "R";
    public static final String CUSTOMER_INDIVIDUAL_TITLE = "003";

    //Customer Phones
    public static final String CUSTOMER_PHONE_NUMBER = new Faker().number().digits(11);
    public static final String CUSTOMER_COUNTRY_CODE = "GB";
    public static final String CUSTOMER_PHONE_TYPE = "MOBILE";
    public static final Boolean CUSTOMER_PHONE_CONFIRMED = Boolean.TRUE;

    public static final String CUSTOMER_STATUS = "QUALIFIED";

    public static final String BRAND = "ES";
    public static final boolean CUSTOMER_MARKET_PERMISSION = Boolean.TRUE;
    public static final String CUSTOMER_CHANNEL = "";
    public static final String CUSTOMER_SOURCE = "";

    public static void generateRequiredCustomerData() {
        CUSTOMER_INDIVIDUAL_FIRST_NAME = new Faker().name().lastName();
        CUSTOMER_INDIVIDUAL_LASTNAME = new Faker().name().firstName();
        HOUSE_NAME = new Faker().address().streetName();
        HOUSE_NUMBER = new Faker().address().buildingNumber();
        CUSTOMER_UNIQUE_EMAIL = StringUtils.generateRandomEmail();
    }

}
