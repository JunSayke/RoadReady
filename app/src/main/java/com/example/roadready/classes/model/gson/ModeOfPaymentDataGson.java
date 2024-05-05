package com.example.roadready.classes.model.gson;

import com.example.roadready.classes.model.gson.data.ModeOfPaymentGson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModeOfPaymentDataGson extends GsonData {
    @SerializedName("modeofpayments")
    public List<ModeOfPaymentGson> modeOfPayments;

    public List<ModeOfPaymentGson> getModeOfPayments() {
        return modeOfPayments;
    }
}
