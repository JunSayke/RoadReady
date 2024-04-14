package com.example.core.model.gson;

import com.example.core.model.gson.data.UserGson;
import com.google.gson.annotations.SerializedName;

public class UserDataGson extends GsonData {
    @SerializedName("user")
    private UserGson userGson;

    public UserGson getUserGson() {
        return userGson;
    }
}