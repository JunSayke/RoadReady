package com.example.roadready.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.example.roadready.R;
import com.example.roadready.classes.general.SessionManager;
import com.example.roadready.databinding.ActivityBuyerHomepageBinding;
import com.example.roadready.databinding.ActivityCashPaymentFormBinding;

import java.util.Map;

public class BuyerHomepage_Activity extends AppCompatActivity {
    private final String TAG = "BuyerHomepage_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivityBuyerHomepageBinding binding; // use View binding to avoid using too much findViewById

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyerHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SessionManager sessionManager = new SessionManager(BuyerHomepage_Activity.this);
        Map<String, String> userData = sessionManager.getUserData();

        assert userData != null;
        binding.bhTvUserWelcome.setText("Welcome " + userData.get("firstname"));
        sessionManager.stopSession();
    }
}