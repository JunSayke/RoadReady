package com.example.roadready.classes.model.gson;

import androidx.annotation.NonNull;

import com.example.roadready.classes.model.gson.data.DealershipGson;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DealershipsDataGson extends GsonData {
    private List<DealershipGson> dealerships;

    public List<DealershipGson> getDealerships() {
        return dealerships;
    }
}
