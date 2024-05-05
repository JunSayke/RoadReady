package com.example.roadready.classes.model.gson;

import com.example.roadready.classes.model.gson.data.ApplicationGson;

import java.util.List;

public class ApplicationsDataGson extends GsonData {
    private List<ApplicationGson> applications;

    public List<ApplicationGson> getApplications() {
        return applications;
    }
}
