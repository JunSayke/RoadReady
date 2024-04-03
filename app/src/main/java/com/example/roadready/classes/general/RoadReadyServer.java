package com.example.roadready.classes.general;

import android.util.Log;

import com.example.roadready.classes.retrofit.RetrofitFacade;

import java.net.HttpCookie;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoadReadyServer extends RetrofitFacade {
    private final String TAG = "RoadReadyServer";

    public RoadReadyServer() {
        super("https://road-ready-black.vercel.app");
    }

    public void addCookies(Set<String> cookies) {
        RetrofitFacade.cookies.addAll(cookies);
    }

    public void removeCookies(Set<String> cookies) {
        RetrofitFacade.cookies.removeAll(cookies);
    }

    public Set<String> parseCookies(String cookies) {
        Log.d(TAG, cookies);
        List<HttpCookie> parseCookies = HttpCookie.parse(cookies);
        Set<String> output = new HashSet<>();
        for (HttpCookie cookie : parseCookies) {
            output.add(cookie.toString());
        }
        Log.d(TAG, output.toString());
        return output;
    }

    public String getCookies() {
        String cookies = RetrofitFacade.cookies.toString();
        return cookies.substring(1, cookies.length() - 1);
    }

    public Set<String> getParseCookies() {
        return parseCookies(getCookies());
    }
}


