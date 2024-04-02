package com.example.roadready.classes.general;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.roadready.classes.model.gson.CookiesGson;
import com.example.roadready.classes.model.gson.data.BuyerGson;
import com.google.gson.Gson;

import java.util.Set;

public class SessionManager {
    private final SharedPreferences sharedpreferences;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedpreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public void startSession(BuyerGson buyerGson, CookiesGson cookiesGson) {
        editor.putString("usergson", new Gson().toJson(buyerGson));
        editor.putString("cookiesgson", new Gson().toJson(cookiesGson));
        editor.apply();
    }

    public void stopSession() {
        editor.clear();
        editor.apply();
    }

    public BuyerGson getUserGson() {
        return new Gson().fromJson(sharedpreferences.getString("usergson", null), BuyerGson.class);
    }

    public CookiesGson getCookiesGson() {
        return new Gson().fromJson(sharedpreferences.getString("cookiesgson", null), CookiesGson.class);
    }
}
