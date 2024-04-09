package com.example.roadready.classes.model.gson;

import com.example.roadready.classes.model.gson.data.UserGson;
import com.google.gson.annotations.SerializedName;

public class UserDataGson extends GsonData {
    @SerializedName("user")
    private UserGson userGson;

    public UserGson getUserGson() {
        return userGson;
    }
}