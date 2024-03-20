package com.example.roadready.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.roadready.classes.general.OkHttp;
import com.example.roadready.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Login_Activity extends AppCompatActivity {
    private final String TAG = "Login_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivityLoginBinding binding; // use View binding to avoid using too much findViewById
    private ProgressBar progressBar; // Declare ProgressBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the ProgressBar
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);

        // Add the progress bar to the ConstraintLayout
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        binding.getRoot().addView(progressBar, params);

        // Initially hide the progress bar
        progressBar.setVisibility(View.GONE);

        binding.loginBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                binding.loginBtnLogin.setEnabled(false);

                String email = String.valueOf(binding.loginIptEmail.getText());
                String password = String.valueOf(binding.loginIptPassword.getText());

                JSONObject data = new JSONObject();
                try {
                    data.put("email", email);
                    data.put("password", password);
                    data.put("loginType", "buyer");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                OkHttp.post(Login_Activity.this, "https://road-ready-black.vercel.app/user/login", data,
                        new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        binding.loginBtnLogin.setEnabled(true);
                                    }
                                });
                                Log.e(TAG, "Error occurred during GET request: " + e.getMessage());
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        binding.loginBtnLogin.setEnabled(true);
                                    }
                                });
                                if (response.isSuccessful()) {
                                    String responseBody = response.body().string();
                                    Log.d(TAG, responseBody);
                                    try {
                                        JSONObject data = new JSONObject(responseBody);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(Login_Activity.this, "Login successfully!", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        Intent intent = new Intent(Login_Activity.this, BuyerHomepage_Activity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Log.e(TAG, "Error response code: " + response.code());
                                    Log.e(TAG, "Error response body: " + response.body().string());
                                }
                            }
                        }
                );
            }
        });
    }
}