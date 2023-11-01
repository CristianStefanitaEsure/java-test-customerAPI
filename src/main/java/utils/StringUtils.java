package utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class StringUtils {

    private static final String EMAIL_SUFFIX = "@customerteam.test";

    public static String applyTimeFormatter(LocalDateTime formattedDateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return formattedDateTime.format(format);
    }

    public static String getCurrentDate(final String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static String getEnhancedDate() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    public static String generateRandomString(int stringLength) {
        return RandomStringUtils.randomAlphabetic(stringLength);
    }

    public static String generateRandomEmail() {
        return RandomStringUtils.randomAlphabetic(20).toLowerCase() + EMAIL_SUFFIX;
    }

}
