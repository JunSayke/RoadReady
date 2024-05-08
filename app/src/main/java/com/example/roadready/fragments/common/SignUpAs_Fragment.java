package com.example.roadready.fragments.common;

import android.content.Intent;
import android.net.Uri;
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
import com.example.roadready.classes.model.gson.data.GoogleAuthGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.databinding.FragmentCommonSignUpAsBinding;

public class SignUpAs_Fragment extends Fragment {
    private final String TAG = "SignUpAs_Fragment";
    private FragmentCommonSignUpAsBinding binding;
    private MainFacade mainFacade;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCommonSignUpAsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        mainFacade.hideProgressBar();
        mainFacade.hideBackDrop();

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
        binding.spasTextLogin.setOnClickListener(v -> {
            mainFacade.getCommonMainNavController().navigate(R.id.action_global_login_Fragment);
        });

        binding.spasBtnVehicleBuyer.setOnClickListener(v -> {
            mainFacade.getCommonMainNavController().navigate(R.id.action_signUpAs_Fragment_to_buyer_signUp_Fragment);
        });

        binding.spasBtnSignupGoogle.setOnClickListener(v -> {
            processGoogleAuth();
            //mainFacade.makeToast("Login with google is not yet available!", Toast.LENGTH_SHORT);
        });

        binding.spasBtnDealer.setOnClickListener(v -> {
            //mainFacade.makeToast("Dealer Registration is not yet available!", Toast.LENGTH_SHORT);
            mainFacade.getCommonMainNavController().navigate(R.id.action_signUpAs_Fragment_to_dealership_SignUp_Fragment);
        });
    }

    private void processGoogleAuth() {
        mainFacade.showBackDrop();
        mainFacade.showProgressBar();
        mainFacade.getGoogleAuthLink(new RoadReadyServer.ResponseListener<GoogleAuthGson>() {
            @Override
            public void onSuccess(SuccessGson<GoogleAuthGson> response) {
                String authenticationUrl = response.getData().getAuthorizationUrl();

                Intent googleIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(authenticationUrl));
                startActivity(googleIntent);
                mainFacade.hideBackDrop();
                mainFacade.hideProgressBar();
            }

            @Override
            public void onFailure(int code, String message) {
                if (code != -1)
                    mainFacade.makeToast(message, Toast.LENGTH_SHORT);
                mainFacade.hideBackDrop();
                mainFacade.hideProgressBar();
            }
        });
    }
}