package com.example.core.model.livedata;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.core.model.gson.data.UserGson;


public class UserGsonViewModel extends ViewModel {
    private static final String TAG = "UserGsonViewModel";
    private final MutableLiveData<UserGson> userGsonLiveData;
    public UserGsonViewModel() {
        userGsonLiveData = new MutableLiveData<>();
    }

    public void setUserGsonLiveData(UserGson userGson) {
        userGsonLiveData.setValue(userGson);
    }

    public LiveData<UserGson> getUserGsonLiveData() {
        return userGsonLiveData;
    }
}
