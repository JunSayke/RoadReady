package com.example.roadready.activity.dealership;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.roadready.databinding.ActivityApprovedBinding;

public class Approved_Activity extends AppCompatActivity {
    private final String TAG = "Approved_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivityApprovedBinding binding; // use View binding to avoid using too much findViewById

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityApprovedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}