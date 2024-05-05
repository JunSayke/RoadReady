package com.example.roadready.fragments.common;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
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
import com.example.roadready.classes.model.gson.data.GoogleAuthGson;
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
            mainFacade.getCommonMainNavController().navigate(R.id.action_login_Fragment_to_signUpAs_Fragment);
        });

        binding.lgnBtnGoogleLogin.setOnClickListener(v -> {
            processGoogleAuth();
        });

        binding.lgnTextForgetPassword.setOnClickListener(v -> {
            mainFacade.makeToast("Forgot password is not yet available!", Toast.LENGTH_SHORT);
        });

        binding.lgnChkTogglePassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.lgnInptPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                binding.lgnInptPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            binding.lgnInptPassword.setSelection(binding.lgnInptPassword.getText().length());
        });
    }

    private void processGoogleAuth() {
        mainFacade.showBackDrop();
        mainFacade.showProgressBar();
        mainFacade.getGoogleAuthLink(new RoadReadyServer.ResponseListener<GoogleAuthGson>() {
            @Override
            public void onSuccess(GoogleAuthGson data) {
                String authenticationUrl = data.getAuthorizationUrl();

                Intent googleIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(authenticationUrl));
                startActivity(googleIntent);
                mainFacade.hideBackDrop();
                mainFacade.hideProgressBar();
            }

            @Override
            public void onFailure(String message) {
                mainFacade.makeToast(message, Toast.LENGTH_SHORT);
                mainFacade.hideBackDrop();
                mainFacade.hideProgressBar();
            }
        });
    }



    private void processLogin() {
        showProgressBar();
        String email = String.valueOf(binding.lgnInptEmail.getText());
        String password = String.valueOf(binding.lgnInptPassword.getText());


        //TODO: separate dealership and buyer
        final RoadReadyServer.ResponseListener<UserDataGson> responseListener = new RoadReadyServer.ResponseListener<UserDataGson>() {
            @Override
            public void onSuccess(UserDataGson data) {
                UserGson user = data.getUserGson();
                mainFacade.startLoginSession(user);
                if(user.getRole().equals("buyer")) {
                    mainFacade.getCommonMainNavController().navigate(R.id.action_login_Fragment_to_homepageContainer_Fragment);
                }else{
                    mainFacade.getCommonMainNavController().navigate(R.id.action_login_Fragment_to_dealership_homepageContainer_Fragment);
                }
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