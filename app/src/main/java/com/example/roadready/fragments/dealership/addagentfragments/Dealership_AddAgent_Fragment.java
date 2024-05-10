package com.example.roadready.fragments.dealership.addagentfragments;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.UserDataGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.databinding.FragmentDealershipAddAgentBinding;

public class Dealership_AddAgent_Fragment extends Fragment {
    private FragmentDealershipAddAgentBinding binding;
    private MainFacade mainFacade;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDealershipAddAgentBinding.inflate(inflater, container, false);
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

    private void initActions() {
        binding.adagntBtnSubmit.setOnClickListener(v -> processRegistration());

        binding.adagntChkTogglePassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.adagntInptPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                binding.adagntInptPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        binding.adagntRbBankAgent.setOnClickListener(v -> {
            binding.adagntBankInputsContainer.setVisibility(View.VISIBLE);
        });

        binding.adagntRbDealershipAgent.setOnClickListener(v -> {
            binding.adagntBankInputsContainer.setVisibility(View.GONE);
        });
    }

    private String getGender(){
        if(binding.adagntRgSexOptions.getCheckedRadioButtonId() != -1){
            return String.valueOf(binding.getRoot().findViewById(binding.adagntRgSexOptions.getCheckedRadioButtonId()).getContentDescription());
        }
        return "";
    }

    private String getAgentType(){
        if(binding.adagntRgAgentTypeOptions.getCheckedRadioButtonId() != -1){
            return String.valueOf(binding.getRoot().findViewById(binding.adagntRgAgentTypeOptions.getCheckedRadioButtonId()).getContentDescription());
        }
        return "";
    }

    private void processRegistration() {
        String email = String.valueOf(binding.adagntInptEmail.getText());
        String password = passwordCheck(String.valueOf(binding.adagntInptPassword.getText()));
        String firstName = String.valueOf(binding.adagntInptFname.getText());
        String lastName = String.valueOf(binding.adagntInptLname.getText());
        String phoneNumber = phoneNumberCheck(String.valueOf(binding.adagntInptPhoneNumber.getText()));
        String address = String.valueOf(binding.adagntInptAddress.getText());
        String gender = getGender();
        String agentType = getAgentType();
        String bankName = String.valueOf(binding.adagntInptBankName.getText());
        String bankAddress = String.valueOf(binding.adagntInptBankAddress.getText());

        if (password.equals("") || phoneNumber.equals(""))
            return;

        showProgressBar();

        final RoadReadyServer.ResponseListener<UserDataGson> responseListener = new RoadReadyServer.ResponseListener<UserDataGson>() {

            @Override
            public void onSuccess(SuccessGson<UserDataGson> response) {
                hideProgressBar();
                mainFacade.makeToast("Agent registered successfully", Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(int code, String message) {
                hideProgressBar();
                if (code != -1)
                    mainFacade.makeToast(message, Toast.LENGTH_SHORT);
            }
        };

        mainFacade.registerAgent(responseListener, email, password, firstName, lastName, phoneNumber, address, gender, agentType, bankName, bankAddress);
    }

    private String passwordCheck(String pass){
        if(pass.length() < 8){
            mainFacade.makeToast("Password must be at least 8 characters long", Toast.LENGTH_SHORT);
            return "";
        }
        return pass;
    }

    private String phoneNumberCheck(String phoneno){
        if(phoneno.length() != 11){
            mainFacade.makeToast("Please enter an 11-digit phone number", Toast.LENGTH_SHORT);
            return "";
        }
        if(phoneno.charAt(0) != '0' || phoneno.charAt(1) != '9'){
            mainFacade.makeToast("Please enter a valid phone number", Toast.LENGTH_SHORT);
            return "";
        }
        return phoneno;
    }

    private void showProgressBar() {
        binding.adagntBtnSubmit.setEnabled(false);
        mainFacade.showProgressBar();
    }

    private void hideProgressBar() {
        binding.adagntBtnSubmit.setEnabled(true);
        mainFacade.hideProgressBar();
    }
}
