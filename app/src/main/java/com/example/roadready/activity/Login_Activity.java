package com.example.roadready.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.roadready.classes.general.SessionManager;
import com.example.roadready.classes.model.gson.LoginDataGson;
import com.example.roadready.classes.model.gson.data.BuyerGson;
import com.example.roadready.classes.model.gson.response.ErrorGson;
import com.example.roadready.classes.model.gson.response.ResponseGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.classes.model.livedata.UserGsonViewModel;
import com.example.roadready.classes.retrofit.RetrofitFacade;
import com.example.roadready.databinding.ActivityLoginBinding;
import com.google.gson.Gson;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {
    private final String TAG = "Login_Activity"; // Declare TAG for each class for debugging purposes using Log.d()
    private ActivityLoginBinding binding; // Use View binding to avoid using too much findViewById
    private final RetrofitFacade retrofitFacade = new RetrofitFacade("https://road-ready-black.vercel.app"); // Declare this if HTTP operations are needed
    private UserGsonViewModel userGsonViewModel; // Use Live Data for efficiently managing real time data changes
    private SessionManager sessionManager; // Holder for user data's and login session
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userGsonViewModel = new ViewModelProvider(this).get(UserGsonViewModel.class);
        sessionManager = new SessionManager(getApplicationContext());
        initActions();
    }

    private void initActions() {
        binding.lgnBtnLogin.setOnClickListener(v -> {
            processLogin();
        });

        binding.lgnTextSignup.setOnClickListener(v -> {
            startActivity(new Intent(Login_Activity.this, SignUp_Activity.class));
        });

        binding.lgnBtnGoogleLogin.setOnClickListener(v -> {
            Toast.makeText(Login_Activity.this, "Login with google is not yet available", Toast.LENGTH_SHORT).show();
        });

        binding.lgnTextForgetPassword.setOnClickListener(v -> {
            Toast.makeText(Login_Activity.this, "Forgot password is not yet available", Toast.LENGTH_SHORT).show();
        });
    }

    private void processLogin() {
        String email = String.valueOf(binding.lgnInptEmail.getText());
        String password = String.valueOf(binding.lgnInptPassword.getText());
        showProgressBar();

        retrofitFacade.getRetrofitService().login(email, password)
                .enqueue(loginCallback);
    }

    private final Callback<SuccessGson<LoginDataGson>> loginCallback = new Callback<SuccessGson<LoginDataGson>>() {
        @Override
        public void onResponse(@NonNull Call<SuccessGson<LoginDataGson>> call, @NonNull Response<SuccessGson<LoginDataGson>> response) {
            ResponseGson body = null;
            try {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    body = response.body();
                    Log.d(TAG, String.valueOf(body));
                    LoginDataGson data = (LoginDataGson) ((SuccessGson<?>) body).getData();
                    BuyerGson user = data.getUserGson();
                    userGsonViewModel.setUserGsonLiveData(user);
                    sessionManager.startSession(user);
                    movingToHomepage();
                } else {
                    assert response.errorBody() != null;
                    body = new Gson().fromJson(response.errorBody().string(), ErrorGson.class);
                    Log.e(TAG, String.valueOf(body));
                }
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            } finally {
                if (body != null) {
                    Toast.makeText(getApplicationContext(), String.valueOf(body.getMessage()), Toast.LENGTH_SHORT).show();
                }
                hideProgressBar();
            }
        }

        @Override
        public void onFailure(@NonNull Call<SuccessGson<LoginDataGson>> call, @NonNull Throwable t) {
            Log.e(TAG, "Login Failed!" + t.getMessage());
            Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
            hideProgressBar();
        }
    };

    private void movingToHomepage() {
        finish();
        startActivity(new Intent(Login_Activity.this, BuyerHomepage_Activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private void showProgressBar() {
        binding.lgnBtnLogin.setEnabled(false);
        binding.pbLoadingBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.lgnBtnLogin.setEnabled(true);
        binding.pbLoadingBar.setVisibility(View.GONE);
    }
}