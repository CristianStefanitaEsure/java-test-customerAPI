package api.requests;

import com.google.gson.Gson;

public class JsonConverter {

    private static final Gson GSON = new Gson();

    public static <T> String toJson(T input){
        return GSON.toJson(input);
    }

}
