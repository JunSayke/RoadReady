package com.example.roadready.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.roadready.databinding.ActivityBuyerProfileBinding;

public class BuyerProfile_Activity extends AppCompatActivity {

    private final String TAG = "BuyerProfile_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivityBuyerProfileBinding binding; // use View binding to avoid using too much findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyerProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}