package utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Wait {
    public static void wait(int milliseconds) {
        try {
            log.debug("Wait: {} milliseconds", milliseconds);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            log.error("Exception: " + e.getMessage() + " Cause " + e.getCause());
            throw new RuntimeException(e);
        }
    }
}
