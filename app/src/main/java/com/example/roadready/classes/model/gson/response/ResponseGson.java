package com.example.roadready.classes.model.gson.response;

import androidx.annotation.NonNull;

import com.example.roadready.classes.model.gson.GsonData;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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
