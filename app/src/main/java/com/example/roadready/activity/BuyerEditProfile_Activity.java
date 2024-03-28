package com.example.roadready.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.roadready.databinding.ActivityBuyerEditProfileBinding;

public class BuyerEditProfile_Activity extends AppCompatActivity {

    private final String TAG = "BuyerEditProfile_Activity"; // Declare TAG for each class for debugging purposes using Log.d()
    private ActivityBuyerEditProfileBinding binding; // Use View binding to avoid using too much findViewById

    private static final int REQUEST_CODE = 1;
    private ActivityResultLauncher<Intent> mapResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyerEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initMapResultLauncher();
        initOnClicks();
    }

    private void initOnClicks() {
        binding.bepBtnOpenMaps.setOnClickListener(v -> {
            startGoogleMaps();
        });
    }

    private void startGoogleMaps() {
        Intent intent = new Intent(BuyerEditProfile_Activity.this, GoogleMaps_Activity.class);
        mapResultLauncher.launch(intent);
    }

    private void initMapResultLauncher() {
        mapResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    double latitude = data.getDoubleExtra("latitude", 0);
                    double longitude = data.getDoubleExtra("longitude", 0);

                    String LongLatText = longitude + ", " + latitude;
                    binding.bepInptCoordinates.setText(LongLatText);
                }
            }
        });
    }
}