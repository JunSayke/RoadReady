package com.example.core.model.gson.response;

import com.example.core.model.gson.GsonData;

public abstract class ErrorGson extends GsonData {
    protected boolean status;
    protected String message;

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}