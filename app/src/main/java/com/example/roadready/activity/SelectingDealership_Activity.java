package com.example.roadready.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.roadready.R;
import com.example.roadready.databinding.ActivitySelectingDealershipBinding;

public class SelectingDealership_Activity extends AppCompatActivity {

    private final String TAG = "SelectingDealership_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivitySelectingDealershipBinding binding; // use View binding to avoid using too much findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting_dealership);
    }
}