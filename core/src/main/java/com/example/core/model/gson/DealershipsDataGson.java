package com.example.core.model.gson;

import com.example.core.model.gson.data.DealershipGson;

import java.util.List;

public class DealershipsDataGson extends GsonData {
    private List<DealershipGson> dealerships;

    public List<DealershipGson> getDealerships() {
        return dealerships;
    }
}