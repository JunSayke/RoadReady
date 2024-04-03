package com.example.roadready.classes.general;

import android.content.Context;
import android.content.SharedPreferences;

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

    public void startSession(BuyerGson buyerGson, String cookies) {
        editor.putString("usergson", new Gson().toJson(buyerGson));
        editor.putString("cookies", cookies);
        editor.apply();
    }

    public void stopSession() {
        editor.clear();
        editor.apply();
    }

    public void setUserGson(BuyerGson userGson) {
        editor.putString("usergson", new Gson().toJson(userGson));
        editor.apply();
    }

    public BuyerGson getUserGson() {
        return new Gson().fromJson(sharedpreferences.getString("usergson", null), BuyerGson.class);
    }

    public void setCookies(String cookies) {
        editor.putString("cookies", cookies);
        editor.apply();
    }

    public String getCookies() {
        return sharedpreferences.getString("cookies", null);
    }
}
