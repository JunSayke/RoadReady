package com.example.roadready.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.roadready.R;
import com.example.roadready.databinding.ActivityInstallmentFormBinding;
import com.example.roadready.databinding.ActivityVerificationBinding;

public class InstallmentForm_Activity extends AppCompatActivity {

    private final String TAG = "InstallmentForm_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivityInstallmentFormBinding binding; // use View binding to avoid using too much findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInstallmentFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}