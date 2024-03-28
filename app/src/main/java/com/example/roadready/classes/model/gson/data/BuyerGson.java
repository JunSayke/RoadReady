package com.example.roadready.classes.model.gson.data;

import com.google.gson.annotations.SerializedName;

public class BuyerGson extends UserGson {
    private String address;
    private String gender;
    @SerializedName("createdat")
    private String createdAt;
    @SerializedName("updatedat")
    private String updatedAt;
    private String role;
    @SerializedName("isapproved")
    private boolean isApproved;
    private String token;

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getRole() {
        return role;
    }

    public boolean getIsApproved() {
        return isApproved;
    }

    public String getToken() {
        return token;
    }
}
