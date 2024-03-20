package com.example.roadready.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = String.valueOf(binding.loginIptEmail.getText());
                String password = String.valueOf(binding.loginIptPassword.getText());

                JSONObject data = new JSONObject();
                try {
                    data.put("username", username);
                    data.put("password", password);
                    data.put("loginType", "dealer");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                OkHttp.post(Login_Activity.this, "https://road-ready-black.vercel.app/user/login", data,
                        new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Log.e(TAG, "Error occurred during GET request: " + e.getMessage());
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                if (response.isSuccessful()) {
                                    String responseBody = response.body().string();
                                    Log.d(TAG, responseBody);
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