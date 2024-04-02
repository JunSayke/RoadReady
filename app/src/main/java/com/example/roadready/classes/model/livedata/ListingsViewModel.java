package com.example.roadready.classes.model.livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListingsViewModel extends ViewModel {
    private static final String TAG = "ListingsViewModel";
    private final MutableLiveData<String> modelId;
    public ListingsViewModel(String modelId) {
        this.modelId = new MutableLiveData<>();
        setModelId(modelId);
    }

    public void setModelId(String modelId) {
        this.modelId.setValue(modelId);
    }

    public MutableLiveData<String> getModelId() {
        return modelId;
    }
}
