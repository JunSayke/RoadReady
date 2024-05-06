package com.example.roadready.classes.model.gson;

import androidx.annotation.NonNull;

import com.example.roadready.classes.model.gson.data.DealershipGson;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DealershipsDataGson extends GsonData {
    private DealershipGson dealership;
    private List<DealershipGson> dealerships;
    public DealershipGson getDealership() {
        return dealership;
    }

    public List<DealershipGson> getDealerships() {
        return dealerships;
    }
}