package com.example.core;

import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import java.util.Set;

public class MainFacade {
    protected final FragmentActivity activity;
    protected final SessionManager sessionManager;
    protected final ServerManager serverManager;

    public MainFacade(FragmentActivity activity) {
        this.activity = activity;
        sessionManager = new SessionManager(activity.getApplicationContext());
        serverManager = new ServerManager();

        Set<String> PREF_COOKIES = sessionManager.getCookies();
        if (!PREF_COOKIES.isEmpty()) {
            serverManager.addCookies(PREF_COOKIES);
        }
    }

    public FragmentActivity getActivity() {
        return activity;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }

    public void makeToast(Object message, int duration) {
        Toast.makeText(activity.getApplicationContext(), String.valueOf(message), duration).show();
    }

    public boolean isLoggedIn() {
        return sessionManager.getUserGson() != null;
    }
}

