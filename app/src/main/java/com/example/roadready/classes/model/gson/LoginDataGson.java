package com.example.roadready.classes.model.gson;

import com.example.roadready.classes.model.gson.data.BuyerGson;
import com.google.gson.annotations.SerializedName;

public class LoginDataGson extends GsonData {
    @SerializedName("user")
    private BuyerGson buyerGson;

    public BuyerGson getUserGson() {
        return buyerGson;
    }
}
