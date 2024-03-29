package com.example.roadready.classes.model.livedata;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.roadready.classes.general.SessionManager;
import com.example.roadready.classes.model.gson.data.BuyerGson;


public class BuyerGsonViewModel extends ViewModel {
    private final MutableLiveData<BuyerGson> buyerGsonLiveData;
    private final SessionManager sessionManager;
    public BuyerGsonViewModel(Context context) {
        buyerGsonLiveData = new MutableLiveData<>();
        sessionManager = new SessionManager(context);
        setBuyerGsonLiveData(sessionManager.getUserGson());
    }

    public void setBuyerGsonLiveData(BuyerGson buyerGson) {
        buyerGsonLiveData.setValue(buyerGson);
    }

    public LiveData<BuyerGson> getBuyerGsonLiveData() {
        return buyerGsonLiveData;
    }
}
