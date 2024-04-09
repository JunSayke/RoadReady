package com.example.roadready.classes.general;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.roadready.classes.model.gson.data.UserGson;
import com.google.gson.Gson;

public class SessionManager {
    private final SharedPreferences sharedpreferences;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedpreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public void startSession(UserGson userGson, String cookies) {
        editor.putString("usergson", new Gson().toJson(userGson));
        editor.putString("cookies", cookies);
        editor.apply();
    }

    public void stopSession() {
        editor.clear();
        editor.apply();
    }

    public void setUserGson(UserGson userGson) {
        editor.putString("usergson", new Gson().toJson(userGson));
        editor.apply();
    }

    public UserGson getUserGson() {
        return new Gson().fromJson(sharedpreferences.getString("usergson", null), UserGson.class);
    }

    public void setCookies(String cookies) {
        editor.putString("cookies", cookies);
        editor.apply();
    }

    public String getCookies() {
        return sharedpreferences.getString("cookies", null);
    }
}
