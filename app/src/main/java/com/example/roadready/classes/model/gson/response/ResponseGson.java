package com.example.roadready.classes.model.gson.response;

import com.example.roadready.classes.model.gson.GsonData;

public abstract class ResponseGson extends GsonData {
    protected boolean status;
    protected String message;

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}