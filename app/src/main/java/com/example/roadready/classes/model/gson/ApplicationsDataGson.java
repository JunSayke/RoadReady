package com.example.roadready.classes.model.gson;

import com.example.roadready.classes.model.gson.data.ApplicationGson;

import java.util.List;

public class ApplicationsDataGson extends GsonData {
    private ApplicationGson application;
    private List<ApplicationGson> applications;

    public ApplicationGson getApplication() {
        return application;
    }

    public List<ApplicationGson> getApplications() {
        return applications;
    }
}
