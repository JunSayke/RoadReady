package com.example.roadready.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roadready.R;
import com.example.roadready.databinding.ActivitySignUpAsBinding;

public class SignUpAs_Activity extends AppCompatActivity {

    private final String TAG = "SignUpAs_Activity"; // declare TAG for each class for debuging purposes using Log.d()
    private ActivitySignUpAsBinding binding; // use View binding to avoid using too much findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpAsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}