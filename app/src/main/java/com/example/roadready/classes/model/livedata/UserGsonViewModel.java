package com.example.roadready.classes.model.livedata;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.roadready.classes.general.SessionManager;
import com.example.roadready.classes.model.gson.data.UserGson;


public class UserGsonViewModel extends ViewModel {
    private final MutableLiveData<UserGson> buyerGsonLiveData;
    private final SessionManager sessionManager;
    public UserGsonViewModel(Context context) {
        buyerGsonLiveData = new MutableLiveData<>();
        sessionManager = new SessionManager(context);
        setUserGsonLiveData(sessionManager.getUserGson());
    }

    public void setUserGsonLiveData(UserGson userGson) {
        buyerGsonLiveData.setValue(userGson);
    }

    public LiveData<UserGson> getUserGsonLiveData() {
        return buyerGsonLiveData;
    }
}
