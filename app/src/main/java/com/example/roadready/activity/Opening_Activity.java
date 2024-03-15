package com.example.roadready.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.roadready.R;
import com.example.roadready.classes.general.FirebaseManager;
import com.example.roadready.databinding.ActivityOpeningBinding;

public class Opening_Activity extends AppCompatActivity {
    private final String TAG = "Opening_Activity"; // declare TAG for each class for debuging purposes using Log.d()
    private ActivityOpeningBinding binding; // use View binding to avoid using too much findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOpeningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}