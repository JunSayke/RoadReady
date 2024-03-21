package com.example.roadready.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.roadready.databinding.ActivityOpeningBinding;

public class Opening_Activity extends AppCompatActivity {
    private final String TAG = "Opening_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivityOpeningBinding binding; // use View binding to avoid using too much findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOpeningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        makeSplashScreen();
    }

    private void makeSplashScreen() {
        int SplashScreenDuration = 3000; // Duration in milliseconds
        Intent IntentLoginActivity = new Intent(Opening_Activity.this, Login_Activity.class);

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(SplashScreenDuration);
            } catch (InterruptedException e) {
                Log.e("Opening_Activity", "InterruptedException occurred: " + e.getMessage(), e);
            } catch (Exception e) {
                Log.e("Opening_Activity", "Exception occurred: " + e.getMessage(), e);
            } finally {
                startActivity(IntentLoginActivity);
            }
        });

        thread.start();
    }
}