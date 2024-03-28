package com.example.roadready.activity.buyer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.roadready.databinding.ActivityVehicleRegistrationProgressBinding;

public class VehicleRegistrationProgress_Activity extends AppCompatActivity {

    private final String TAG = "VehicleRegistrationProgress_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivityVehicleRegistrationProgressBinding binding; // use View binding to avoid using too much findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVehicleRegistrationProgressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}