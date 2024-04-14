package com.example.core.model.gson;

import com.example.core.model.gson.data.VehicleGson;

import java.util.List;

public class ListingsDataGson extends GsonData {
    private List<VehicleGson> listings;

    public List<VehicleGson> getListings() {
        return listings;
    }
}