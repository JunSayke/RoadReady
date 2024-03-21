package com.example.roadready.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.roadready.R;
import com.example.roadready.databinding.ActivityBuyerHomepageBinding;
import com.example.roadready.databinding.ActivityCashPaymentFormBinding;
import com.example.roadready.databinding.ActivityOpeningBinding;

public class CashPaymentForm_Activity extends AppCompatActivity {
    private final String TAG = "BuyerHomepage_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivityCashPaymentFormBinding binding; // use View binding to avoid using too much findViewById
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCashPaymentFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}