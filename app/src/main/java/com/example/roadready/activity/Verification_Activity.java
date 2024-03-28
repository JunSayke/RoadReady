package com.example.roadready.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roadready.databinding.ActivityVerificationBinding;

public class Verification_Activity extends AppCompatActivity {

    private final String TAG = "Verification_Activity"; // Declare TAG for each class for debugging purposes using Log.d()
    private ActivityVerificationBinding binding; // Use View binding to avoid using too much findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initActions();
    }

    private void initActions() {
        binding.vrfTextResendCode.setOnClickListener(v -> {

        });

        binding.vrfBtnSubmit.setOnClickListener(v -> {
            Log.d(TAG, String.valueOf(binding.vrfInptOtp.getText()));
        });
    }
}