package com.example.roadready.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.roadready.classes.general.OkHttp;
import com.example.roadready.databinding.ActivitySignUpBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignUp_Activity extends AppCompatActivity {

    private final String TAG = "SignUp_Activity";
    private ActivitySignUpBinding binding;
    private ProgressBar progressBar; // Declare ProgressBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
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

        binding.signupBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the progress bar when the button is clicked
                progressBar.setVisibility(View.VISIBLE);
                binding.signupBtnSubmit.setEnabled(false);

                // Retrieve user input data
                String firstName = binding.signupIptFname.getText().toString();
                String lastName = binding.signupIptLname.getText().toString();
                String email = binding.signupIptEmail.getText().toString();
                String phone = binding.signupIptPhone.getText().toString();
                String password = binding.signupIptPassword.getText().toString();
                String address = binding.signupIptAddress.getText().toString();
                String longitudeAndLatitude = binding.signupIptCoordinates.getText().toString();
                String sex = ((RadioButton) findViewById(binding.signupRgSexOptions.getCheckedRadioButtonId())).getHint().toString();

                // Create JSON object with user data
                JSONObject data = new JSONObject();
                try {
                    data.put("firstName", firstName);
                    data.put("lastName", lastName);
                    data.put("email", email);
                    data.put("password", password);
                    data.put("gender", sex);
                    data.put("phoneNumber", phone);
                    data.put("address", address);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Perform registration request using OkHttp
                OkHttp.post(SignUp_Activity.this, "https://road-ready-black.vercel.app/buyer/register", data,
                        new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                // Hide the progress bar if there's an error
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        binding.signupBtnSubmit.setEnabled(true);
                                    }
                                });
                                Log.e(TAG, "Error occurred during registration: " + e.getMessage());
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                // Hide the progress bar when the response is received
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        binding.signupBtnSubmit.setEnabled(true);
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
                                                Toast.makeText(SignUp_Activity.this, "Registered Successfully!", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        Intent intent = new Intent(SignUp_Activity.this, BuyerHomepage_Activity.class);
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
                        });
            }
        });
    }
}
