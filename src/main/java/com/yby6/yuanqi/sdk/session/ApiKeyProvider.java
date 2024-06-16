package com.yby6.yuanqi.sdk.session;

public class ApiKeyProvider {
    private static volatile String apiKey;

    public static String getApiKey() {
        return apiKey;
    }

    public static void setApiKey(String newApiKey) {
        apiKey = newApiKey;
    }
}
