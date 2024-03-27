package com.example.roadready.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.roadready.databinding.ActivityVehicleApplicationProgressBinding;

public class VehicleApplicationProgress_Activity extends AppCompatActivity {

    private final String TAG = "VehicleApplicationProgress_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivityVehicleApplicationProgressBinding binding; // use View binding to avoid using too much findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVehicleApplicationProgressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}