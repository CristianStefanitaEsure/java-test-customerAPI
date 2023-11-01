package utils.constants;

import lombok.Getter;

public enum LegitimateChannels {

    EMAIL("Email"),
    SMS("SMS"),
    POST("Post"),
    PHONE("Phone");

    @Getter
    private String channel;

    LegitimateChannels(String channel) {
        this.channel = channel;
    }
}
