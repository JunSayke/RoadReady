package com.example.roadready.fragments.common;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.model.gson.LoginDataGson;
import com.example.roadready.classes.model.gson.data.BuyerGson;
import com.example.roadready.classes.model.gson.response.ErrorGson;
import com.example.roadready.classes.model.gson.response.ResponseGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.databinding.FragmentLoginBinding;
import com.google.gson.Gson;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Fragment extends Fragment {
    private final String TAG = "Login_Fragment";
    private FragmentLoginBinding binding;
    private MainFacade mainFacade;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initActions();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initActions() {
        binding.lgnBtnLogin.setOnClickListener(v -> {
            processLogin();
        });

        binding.lgnTextSignup.setOnClickListener(v -> {
            mainFacade.getMainNavGraphController().navigate(R.id.action_login_Fragment_to_signUpAs_Fragment);
        });

        binding.lgnBtnGoogleLogin.setOnClickListener(v -> {
            mainFacade.makeToast("Login with google is not yet available!", Toast.LENGTH_SHORT);
        });

        binding.lgnTextForgetPassword.setOnClickListener(v -> {
            mainFacade.makeToast("Forgot password is not yet available", Toast.LENGTH_SHORT);
        });
    }

    private void processLogin() {
        String email = String.valueOf(binding.lgnInptEmail.getText());
        String password = String.valueOf(binding.lgnInptPassword.getText());
        showProgressBar();

        mainFacade.getServer().getRetrofitService().login(email, password)
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
                    mainFacade.startLoginSession(user, mainFacade.getServer().getCookiesGson());
                    mainFacade.getMainNavGraphController().navigate(R.id.action_login_Fragment_to_homepageContainer_Fragment);
                } else {
                    assert response.errorBody() != null;
                    body = new Gson().fromJson(response.errorBody().string(), ErrorGson.class);
                    Log.e(TAG, String.valueOf(body));
                }
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            } finally {
                if (body != null) {
                    mainFacade.makeToast(body.getMessage(), Toast.LENGTH_SHORT);
                }
                hideProgressBar();
            }
        }

        @Override
        public void onFailure(@NonNull Call<SuccessGson<LoginDataGson>> call, @NonNull Throwable t) {
            Log.e(TAG, "Login Failed!" + t.getMessage());
            mainFacade.makeToast("Network Error!", Toast.LENGTH_SHORT);
            hideProgressBar();
        }
    };

    private void showProgressBar() {
        binding.lgnBtnLogin.setEnabled(false);
        mainFacade.showProgressBar();
    }

    private void hideProgressBar() {
        binding.lgnBtnLogin.setEnabled(true);
        mainFacade.hideProgressBar();
    }
}