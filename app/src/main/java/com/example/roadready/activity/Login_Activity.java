package com.example.roadready.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.roadready.R;
import com.example.roadready.databinding.ActivityLoginBinding;
import com.example.roadready.databinding.ActivityOpeningBinding;

public class Login_Activity extends AppCompatActivity {
    private ActivityLoginBinding binding; // use View binding to avoid using too much findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}