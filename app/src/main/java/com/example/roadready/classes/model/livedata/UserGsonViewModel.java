package com.example.roadready.classes.model.livedata;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.roadready.classes.model.gson.data.BuyerGson;


public class UserGsonViewModel extends ViewModel {
    private final MutableLiveData<BuyerGson> userGsonLiveData = new MutableLiveData<>();

    public void setUserGsonLiveData(BuyerGson buyerGson) {
        userGsonLiveData.setValue(buyerGson);
    }

    public LiveData<BuyerGson> getUserGsonLiveData() {
        return userGsonLiveData;
    }
}
