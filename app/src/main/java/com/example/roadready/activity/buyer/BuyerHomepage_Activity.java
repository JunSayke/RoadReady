package com.example.roadready.activity.buyer;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.roadready.databinding.ActivityBuyerHomepageBinding;

public class BuyerHomepage_Activity extends AppCompatActivity {
    private final String TAG = "BuyerHomepage_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivityBuyerHomepageBinding binding; // use View binding to avoid using too much findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyerHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}