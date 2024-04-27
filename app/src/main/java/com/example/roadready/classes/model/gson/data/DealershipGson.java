package com.example.roadready.classes.model.gson.data;

import com.example.roadready.classes.model.gson.GsonData;
import com.google.gson.annotations.SerializedName;

public class DealershipGson extends GsonData {
    private String id;
    @SerializedName("image")
    private String dealershipImageUrl;
    private String name;
    private UserGson manager;
    private String latitude;
    private String longitude;
    private String address;
    @SerializedName("createdat")
    private String createdAt;
    @SerializedName("updatedat")
    private String updatedAt;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDealershipImageUrl() {
        return dealershipImageUrl;
    }

    public UserGson getManager() {
        return manager;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
