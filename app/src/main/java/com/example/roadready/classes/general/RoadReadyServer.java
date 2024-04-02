package com.example.roadready.classes.general;

import com.example.roadready.classes.model.gson.CookiesGson;
import com.example.roadready.classes.retrofit.RetrofitFacade;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RoadReadyServer extends RetrofitFacade {
    public RoadReadyServer() {
        super("https://road-ready-black.vercel.app");
    }

    public void addCookies(Set<String> cookies) {
        RetrofitFacade.cookies.addAll(cookies);
    }

    public void removeCookies(Set<String> cookies) {
        RetrofitFacade.cookies.removeAll(cookies);
    }

    public String getCookies() {
        String cookies = RetrofitFacade.cookies.toString();

        String[] keyValuePairs = cookies.substring(1, cookies.length() - 1).split(", ");
        Map<String, String> map = new HashMap<>();

        for (String pair : keyValuePairs) {
            String[] data = pair.split("=");
            map.put(data[0], data[1]);
        }

        return new Gson().toJson(map);
    }

    public CookiesGson getCookiesGson() {
        return new Gson().fromJson(getCookies(), CookiesGson.class);
    }
}
