package com.example.roadready.classes.model.livedata;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BuyerGsonViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public BuyerGsonViewModelFactory(Context context) {
        this.context = context.getApplicationContext(); // Use application context to avoid memory leaks
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BuyerGsonViewModel.class)) {
            return (T) new BuyerGsonViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
