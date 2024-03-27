package com.example.roadready.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roadready.databinding.ActivitySignUpAsBinding;

public class SignUpAs_Activity extends AppCompatActivity {

    private final String TAG = "SignUpAs_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivitySignUpAsBinding binding; // use View binding to avoid using too much findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpAsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.spasTextLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpAs_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        });

        binding.spasBtnVehicleBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpAs_Activity.this, SignUp_Activity.class);
                startActivity(intent);
            }
        });
    }
}