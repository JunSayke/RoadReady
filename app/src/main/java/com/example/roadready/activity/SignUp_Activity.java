package com.example.roadready.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roadready.R;
import com.example.roadready.databinding.ActivitySignUpBinding;

public class SignUp_Activity extends AppCompatActivity {

    private final String TAG = "SignUp_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivitySignUpBinding binding; // use View binding to avoid using too much findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}