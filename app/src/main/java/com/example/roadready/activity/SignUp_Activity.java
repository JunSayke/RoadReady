package com.example.roadready.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.roadready.classes.general.OkHttp;
import com.example.roadready.classes.general.SessionManager;
import com.example.roadready.classes.model.gson.LoginDataGson;
import com.example.roadready.classes.model.gson.data.BuyerGson;
import com.example.roadready.classes.model.gson.response.ErrorGson;
import com.example.roadready.classes.model.gson.response.ResponseGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.classes.model.livedata.UserGsonViewModel;
import com.example.roadready.classes.retrofit.RetrofitFacade;
import com.example.roadready.databinding.ActivitySignUpBinding;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class SignUp_Activity extends AppCompatActivity {
    private final String TAG = "SignUp_Activity"; // Declare TAG for each class for debugging purposes using Log.d()
    private ActivitySignUpBinding binding; // Use View binding to avoid using too much findViewById
    private final RetrofitFacade retrofitFacade = new RetrofitFacade("https://road-ready-black.vercel.app"); // Declare this if HTTP operations are needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initActions();
    }

    private void initActions() {
        binding.sgnupBtnSubmit.setOnClickListener(v -> {
            processRegistration();
        });

        binding.sgnupTextLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignUp_Activity.this, Login_Activity.class));
        });

        binding.sgnupBtnGoogleLogin.setOnClickListener(v -> {
            Toast.makeText(SignUp_Activity.this, "Login with google is not yet available", Toast.LENGTH_SHORT).show();
        });
    }

    private void processRegistration() {
        showProgressBar();

        String email = String.valueOf(binding.sgnupInptEmail.getText());
        String password = String.valueOf(binding.sgnupInptPassword.getText());
        String firstName = String.valueOf(binding.sgnupInptFname.getText());
        String lastName = String.valueOf(binding.sgnupInptLname.getText());
        String phoneNumber = String.valueOf(binding.sgnupInptPhoneNumber.getText());
        String gender = String.valueOf(findViewById(binding.sgnupRgSexOptions.getCheckedRadioButtonId()).getContentDescription());
        String address = String.valueOf(binding.sgnupInptAddress.getText());

        retrofitFacade.getRetrofitService().register(email, password, firstName, lastName, phoneNumber, gender, address)
                .enqueue(registerCallback);
    }

    private final Callback< SuccessGson<Nullable> > registerCallback = new Callback< SuccessGson<Nullable> >() {

        @Override
        public void onResponse(@NonNull Call<SuccessGson<Nullable>> call, @NonNull Response<SuccessGson<Nullable>> response) {
            ResponseGson body = null;
            try {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    body = response.body();
                    Log.d(TAG, String.valueOf(body));
                    movingToVerification();
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
        public void onFailure(@NonNull Call<SuccessGson<Nullable>> call, @NonNull Throwable t) {
            Log.e(TAG, "Registration Failed!" + t.getMessage());
            Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
            hideProgressBar();
        }
    };

    private void movingToVerification() {
        finish();
        startActivity(new Intent(SignUp_Activity.this, Verification_Activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private void showProgressBar() {
        binding.sgnupBtnSubmit.setEnabled(false);
        binding.pbLoadingBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.sgnupBtnSubmit.setEnabled(true);
        binding.pbLoadingBar.setVisibility(View.GONE);
    }
}
