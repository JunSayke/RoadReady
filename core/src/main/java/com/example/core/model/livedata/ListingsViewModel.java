package com.example.core.model.livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListingsViewModel extends ViewModel {
    private static final String TAG = "ListingsViewModel";
    private final MutableLiveData<String> modelIdLiveData;
    public ListingsViewModel() {
        this.modelIdLiveData = new MutableLiveData<>();
    }

    public void setModelIdLiveData(String modelIdLiveData) {
        this.modelIdLiveData.setValue(modelIdLiveData);
    }

    public MutableLiveData<String> getModelIdLiveData() {
        return modelIdLiveData;
    }
}
