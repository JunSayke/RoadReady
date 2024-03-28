package com.example.roadready.activity.dealership;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.roadready.databinding.ActivityBankBinding;

public class Bank_Activity extends AppCompatActivity {
    private final String TAG = "Bank_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivityBankBinding binding; // use View binding to avoid using too much findViewById

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityBankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}