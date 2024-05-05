package com.example.roadready.classes.model.gson.data;

import com.example.roadready.classes.model.gson.GsonData;
import com.google.gson.annotations.SerializedName;

public class ModeOfPaymentGson extends GsonData {
    private String id;
    @SerializedName("dealership")
    private String dealershipId;
    @SerializedName("modeofpayment")
    private String modeOfPayment;
    @SerializedName("createdat")
    private String createdAt;
    @SerializedName("updatedat")
    private String updatedAt;

    public String getId() {
        return id;
    }

    public String getDealershipId() {
        return dealershipId;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
