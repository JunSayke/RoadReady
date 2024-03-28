package com.example.roadready.classes.model.gson;

import com.example.roadready.classes.model.gson.data.VehicleGson;

import java.util.List;

public class ListingsDataGson extends GsonData {
    private List<VehicleGson> listings;

    public List<VehicleGson> getListings() {
        return listings;
    }
}
