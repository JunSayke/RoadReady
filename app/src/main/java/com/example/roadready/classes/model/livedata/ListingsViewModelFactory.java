package com.example.roadready.classes.model.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ListingsViewModelFactory implements ViewModelProvider.Factory {
    private final String  modelId;

    public ListingsViewModelFactory(String modelId) {
        this.modelId = modelId;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListingsViewModel.class)) {
            return (T) new ListingsViewModel(modelId);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
