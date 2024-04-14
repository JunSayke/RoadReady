package com.example.core.model.gson.response;

public class SuccessGson<T> extends ErrorGson {
    private T data;

    public T getData() {
        return data;
    }
}