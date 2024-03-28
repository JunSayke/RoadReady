package com.example.roadready.activity.buyer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.example.roadready.databinding.ActivitySelectingCarBinding;

public class SelectingCar_Activity extends AppCompatActivity {

    private final String TAG = "SelectingCar_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivitySelectingCarBinding binding; // use View binding to avoid using too much findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectingCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}