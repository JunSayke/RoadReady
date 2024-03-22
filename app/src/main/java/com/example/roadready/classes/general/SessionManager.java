package com.example.roadready.classes.general;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private final SharedPreferences sharedpreferences;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedpreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public void startSession(String userid, String firstname, String lastname) {
        editor.putString("userid", userid);
        editor.putString("firstname", firstname);
        editor.putString("lastname", lastname);
        editor.apply();
    }

    public void stopSession() {
        editor.clear();
        editor.apply();
    }

    public Map<String, String> getUserData() {
        final Map<String, String> userData;
        userData = new HashMap<>();
        userData.put("userid", sharedpreferences.getString("userid", null));

        if (userData.get("userid") == null) {
            return null;
        }

        userData.put("firstname", sharedpreferences.getString("firstname", null));
        userData.put("lastname", sharedpreferences.getString("lastname", null));
        return userData;
    }
}
