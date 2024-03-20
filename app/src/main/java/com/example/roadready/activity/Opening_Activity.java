package com.example.roadready.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.roadready.R;
import com.example.roadready.classes.general.FirebaseManager;
import com.example.roadready.databinding.ActivityOpeningBinding;

public class Opening_Activity extends AppCompatActivity {
    private final String TAG = "Opening_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivityOpeningBinding binding; // use View binding to avoid using too much findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOpeningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //  Splash Screen code
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(3000); //  Duration
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                startActivity(new Intent(Opening_Activity.this, Login_Activity.class));
            }
        });

        thread.start();
    }
}