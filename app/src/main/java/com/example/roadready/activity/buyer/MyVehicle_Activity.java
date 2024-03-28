package com.example.roadready.activity.buyer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.roadready.databinding.ActivityMyVehicleBinding;

public class MyVehicle_Activity extends AppCompatActivity {

    private final String TAG = "MyVehicle_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivityMyVehicleBinding binding; // use View binding to avoid using too much findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyVehicleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}