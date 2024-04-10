package com.example.roadready.fragments.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.UserDataGson;
import com.example.roadready.classes.model.gson.data.UserGson;
import com.example.roadready.databinding.FragmentCommonLoginBinding;

public class Login_Fragment extends Fragment {
    private final String TAG = "Login_Fragment";
    private FragmentCommonLoginBinding binding;
    private MainFacade mainFacade;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCommonLoginBinding.inflate(inflater, container, false);
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
            mainFacade.getBuyerMainNavController().navigate(R.id.action_login_Fragment_to_signUpAs_Fragment);
        });

        binding.lgnBtnGoogleLogin.setOnClickListener(v -> {
            mainFacade.makeToast("Login with google is not yet available!", Toast.LENGTH_SHORT);
        });

        binding.lgnTextForgetPassword.setOnClickListener(v -> {
            mainFacade.makeToast("Forgot password is not yet available!", Toast.LENGTH_SHORT);
        });
    }

    private void processLogin() {
        showProgressBar();
        String email = String.valueOf(binding.lgnInptEmail.getText());
        String password = String.valueOf(binding.lgnInptPassword.getText());

        final RoadReadyServer.ResponseListener<UserDataGson> responseListener = new RoadReadyServer.ResponseListener<UserDataGson>() {
            @Override
            public void onSuccess(UserDataGson data) {
                UserGson user = data.getUserGson();
                mainFacade.startLoginSession(user);
                mainFacade.getBuyerMainNavController().navigate(R.id.action_login_Fragment_to_homepageContainer_Fragment);
                hideProgressBar();
            }

            @Override
            public void onFailure(String message) {
                mainFacade.makeToast(message, Toast.LENGTH_SHORT);
                hideProgressBar();
            }
        };

        mainFacade.login(responseListener, email, password);
    }

    private void showProgressBar() {
        binding.lgnBtnLogin.setEnabled(false);
        mainFacade.showProgressBar();
    }

    private void hideProgressBar() {
        binding.lgnBtnLogin.setEnabled(true);
        mainFacade.hideProgressBar();
    }
}