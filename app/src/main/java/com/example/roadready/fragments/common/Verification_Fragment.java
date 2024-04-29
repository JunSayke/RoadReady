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
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.GsonData;
import com.example.roadready.classes.model.gson.UserDataGson;
import com.example.roadready.classes.model.gson.data.UserGson;
import com.example.roadready.databinding.FragmentCommonVerificationBinding;

public class Verification_Fragment extends Fragment {
    private final String TAG = "Verification_Fragment";
    private FragmentCommonVerificationBinding binding;
    private MainFacade mainFacade;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCommonVerificationBinding.inflate(inflater, container, false);
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
        binding.vrfTextResendCode.setOnClickListener(v -> {
            RoadReadyServer.ResponseListener<GsonData> responseListener = new RoadReadyServer.ResponseListener<GsonData>() {
                @Override
                public void onSuccess(GsonData data) {

                }

                @Override
                public void onFailure(String message) {
                    mainFacade.makeToast(message, Toast.LENGTH_SHORT);
                }
            };
            mainFacade.requestOTP(responseListener);
        });

        binding.vrfBtnSubmit.setOnClickListener(v -> {
            showProgressBar();
            String code = binding.vrfInptOtp.getText().toString();
            if(code.length() != 4){
                hideProgressBar();
                mainFacade.makeToast("Enter the code correctly!", Toast.LENGTH_SHORT);
                return;
            }
            RoadReadyServer.ResponseListener<UserDataGson> responseListener = new RoadReadyServer.ResponseListener<UserDataGson>() {
                @Override
                public void onSuccess(UserDataGson data) {
                    hideProgressBar();
                    mainFacade.getBuyerHomepageNavController().navigate(R.id.action_verification_Fragment_to_mnHome);
                }

                @Override
                public void onFailure(String message) {
                    hideProgressBar();
                    mainFacade.makeToast(message, Toast.LENGTH_SHORT);
                }
            };
            mainFacade.verifyBuyerOTP(responseListener, code);
        });
    }

    private void showProgressBar() {
        binding.vrfBtnSubmit.setEnabled(false);
        mainFacade.showProgressBar();
    }

    private void hideProgressBar() {
        binding.vrfBtnSubmit.setEnabled(true);
        mainFacade.hideProgressBar();
    }
}