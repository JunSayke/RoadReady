package com.example.roadready.classes.model.gson.response;

public class SuccessGson<T> extends ResponseGson {
    private T data;

    public T getData() {
        return data;
    }
}
