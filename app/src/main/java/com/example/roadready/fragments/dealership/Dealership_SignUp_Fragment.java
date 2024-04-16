package com.example.roadready.fragments.dealership;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roadready.R;
import com.example.roadready.activity.GoogleMaps_Activity;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.GsonData;
import com.example.roadready.databinding.FragmentDealershipSignUpBinding;

import java.util.Arrays;
import java.util.List;

public class Dealership_SignUp_Fragment extends Fragment {
    private final String TAG = "SignUp_Fragment";
    private FragmentDealershipSignUpBinding binding;
    private ActivityResultLauncher<Intent> mapResultLauncher;
    private MainFacade mainFacade;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDealershipSignUpBinding.inflate(inflater, container, false);
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

        initMapResultLauncher();
        initActions();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void initActions() {
        binding.sgnupBtnSubmit.setOnClickListener(v -> {
            processRegistration();
        });

        binding.sgnupTextLogin.setOnClickListener(v -> {
            mainFacade.getBuyerMainNavController().navigate(R.id.action_global_login_Fragment);
        });

        binding.sgnupBtnOpenMaps.setOnClickListener(v -> {
            startGoogleMaps();
        });

        binding.sgnupBtnGoogleLogin.setOnClickListener(v -> {
            mainFacade.makeToast("Login with google is not yet available!", Toast.LENGTH_SHORT);
        });
    }

    private void startGoogleMaps() {
        Intent intent = new Intent(mainFacade.getMainActivity().getApplicationContext(), GoogleMaps_Activity.class);
        mapResultLauncher.launch(intent);
    }

    private void initMapResultLauncher() {
        mapResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    double latitude = data.getDoubleExtra("latitude", 0);
                    double longitude = data.getDoubleExtra("longitude", 0);

                    String LongLatText = longitude + ", " + latitude;
                    binding.sgnupInptCoordinates.setText(LongLatText);
                }
            }
        });
    }

    private void processRegistration() {
        String coords = String.valueOf(binding.sgnupInptCoordinates.getText());
        List<String> coordList = Arrays.asList(coords.split(", "));
        showProgressBar();

        String email = String.valueOf(binding.sgnupInptEmail.getText());
        String password = String.valueOf(binding.sgnupInptPassword.getText());
        String firstName = String.valueOf(binding.sgnupInptFname.getText());
        String lastName = String.valueOf(binding.sgnupInptLname.getText());
        String phoneNumber = String.valueOf(binding.sgnupInptPhoneNumber.getText());
        String gender = String.valueOf(binding.getRoot().findViewById(binding.sgnupRgSexOptions.getCheckedRadioButtonId()).getContentDescription());
        String establishmentAddress = String.valueOf(binding.sgnupInptAddress.getText());
        String dealershipName = String.valueOf(binding.sgnupInptDealershipName.getText());
        String latitude = String.valueOf(coordList.get(0));
        String longitude = String.valueOf(coordList.get(1));
        String modeOfPayment = String.valueOf(binding.getRoot().findViewById(binding.sgnupRgModeOfPaymentOptions.getCheckedRadioButtonId()).getContentDescription());

        //TODO: Validations

        final RoadReadyServer.ResponseListener<GsonData> responseListener = new RoadReadyServer.ResponseListener<GsonData>() {
            @Override
            public void onSuccess(GsonData data) {
                hideProgressBar();
            }

            @Override
            public void onFailure(String message) {
                mainFacade.makeToast(message, Toast.LENGTH_SHORT);
                hideProgressBar();
            }
        };

        mainFacade.registerDealership(responseListener, null, email, password, firstName, lastName, phoneNumber, gender, dealershipName, establishmentAddress, latitude, longitude, modeOfPayment);
    }

    private void showProgressBar() {
        binding.sgnupBtnSubmit.setEnabled(false);
        mainFacade.showProgressBar();
    }

    private void hideProgressBar() {
        binding.sgnupBtnSubmit.setEnabled(true);
        mainFacade.hideProgressBar();
    }
}
