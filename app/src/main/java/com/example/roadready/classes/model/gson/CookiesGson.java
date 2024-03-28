package com.example.roadready.classes.model.gson;

import com.google.gson.annotations.SerializedName;

public class CookiesGson extends GsonData {
    private String jwt;
    @SerializedName("access_token")
    private String accessToken;

    public String getJwt() {
        return jwt;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
