package com.example.roadready.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.roadready.databinding.ActivityBuyerEditProfileBinding;

public class BuyerEditProfile_Activity extends AppCompatActivity {

    private final String TAG = "BuyerEditProfile_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivityBuyerEditProfileBinding binding; // use View binding to avoid using too much findViewById
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyerEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}