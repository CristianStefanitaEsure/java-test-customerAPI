package utils;

import lombok.Getter;
import lombok.Setter;

public class Environment {

    private static Environment instance = null;

    @Getter
    @Setter
    public String customerNumber;

    public static Environment getInstance() {
        if (instance == null) {
            instance = new Environment();
        }

        return instance;
    }

}
