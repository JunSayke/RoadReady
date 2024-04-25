package com.example.roadready.fragments.dealership;

import static com.example.roadready.classes.util.GetFileNameFromUri.getFileNameFromUri;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import com.example.roadready.classes.general.FileUtils;
import com.example.roadready.classes.general.ImagePicker;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.GsonData;
import com.example.roadready.classes.model.gson.data.GoogleAuthGson;
import com.example.roadready.databinding.FragmentDealershipSignUpBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dealership_SignUp_Fragment extends Fragment implements ImagePicker.OnImageSelectedListener{
    private final String TAG = "SignUp_Fragment";
    private FragmentDealershipSignUpBinding binding;
    private ActivityResultLauncher<Intent> mapResultLauncher;
    private MainFacade mainFacade;
    private ImagePicker imagePicker;

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

        initImagePicker();

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
            mainFacade.getCommonMainNavController().navigate(R.id.action_global_login_Fragment);
        });

        binding.sgnupBtnOpenMaps.setOnClickListener(v -> {
            startGoogleMaps();
        });

        binding.sgnupBtnGoogleLogin.setOnClickListener(v -> {
            // TODO: Make it registered as dealership somehow. Anyway let jake cook
            processGoogleAuth();
        });

        binding.sgnupBtnUpload.setOnClickListener(v -> {
            imagePicker.selectImage(this);
        });

        binding.sgnupCbBankLoan.setOnClickListener(v -> {
            if(binding.sgnupCbBankLoan.isChecked()){
                binding.sgnupInptBankLoanContainer.setVisibility(View.VISIBLE);
            }else{
                binding.sgnupInptBankLoanContainer.setVisibility(View.GONE);
                binding.sgnupCbDealershipBankChoice.setChecked(false);
                binding.sgnupCbBuyerBankChoice.setChecked(false);
            }
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

    private String getGender(){
        if(binding.sgnupRgSexOptions.getCheckedRadioButtonId() != -1){
            return String.valueOf(binding.getRoot().findViewById(binding.sgnupRgSexOptions.getCheckedRadioButtonId()).getContentDescription());
        }
        return "";
    }

    private List<String> checkCoords(){
        List<String> coordList = new ArrayList<>();
        String coords = String.valueOf(binding.sgnupInptCoordinates.getText());
        if(coords.contains(", ")) {
            coordList = Arrays.asList(coords.split(", "));
            if(Double.parseDouble(coordList.get(0)) < -90 && Double.parseDouble(coordList.get(0)) > 90){
                coordList.set(0, "");
            }
            if(Double.parseDouble(coordList.get(0)) < -180 && Double.parseDouble(coordList.get(0)) > 180){
                coordList.set(1, "");
            }
        }
        else {
            coordList.add("");
            coordList.add("");
        }

        return coordList;
    }

    private String getmodeOfPayments(){
        StringBuilder stringPayments = new StringBuilder();

        if(binding.sgnupCbInhouseFinance.isChecked()) stringPayments.append("inhouseFinance,");
        if(binding.sgnupCbCash.isChecked()) stringPayments.append("cash,");
        if(binding.sgnupCbCheque.isChecked()) stringPayments.append("cheque,");
        if(binding.sgnupCbDealershipBankChoice.isChecked()) stringPayments.append("bankLoan(dealershipBankChoice),");
        if(binding.sgnupCbBuyerBankChoice.isChecked()) stringPayments.append("bankLoan(buyerBankChoice,");

        return stringPayments.toString();
    }

    private void processRegistration() {
        List<String> coordList = checkCoords();
        showProgressBar();

        File dealershipImageFile = FileUtils.drawableToFile(mainFacade.getMainActivity(), R.drawable.roadready, "dealership_image.png");
        String email = String.valueOf(binding.sgnupInptEmail.getText());
        String password = String.valueOf(binding.sgnupInptPassword.getText());
        String firstName = String.valueOf(binding.sgnupInptFname.getText());
        String lastName = String.valueOf(binding.sgnupInptLname.getText());
        String phoneNumber = String.valueOf(binding.sgnupInptPhoneNumber.getText());
        String gender = getGender();
        String establishmentAddress = String.valueOf(binding.sgnupInptAddress.getText());
        String dealershipName = String.valueOf(binding.sgnupInptDealershipName.getText());
        String latitude = String.valueOf(coordList.get(0));
        String longitude = String.valueOf(coordList.get(1));
        String modeOfPayments = getmodeOfPayments();

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

        mainFacade.registerDealership(responseListener, dealershipImageFile, email, password, firstName, lastName, phoneNumber, gender, dealershipName, establishmentAddress, latitude, longitude, modeOfPayments);
    }

    private void showProgressBar() {
        binding.sgnupBtnSubmit.setEnabled(false);
        mainFacade.showProgressBar();
    }

    private void hideProgressBar() {
        binding.sgnupBtnSubmit.setEnabled(true);
        mainFacade.hideProgressBar();
    }

    private void initImagePicker() {
        imagePicker = new ImagePicker(mainFacade.getMainActivity().getActivityResultRegistry());
        getLifecycle().addObserver(imagePicker);
    }

    @Override
    public void onImageSelected(Uri imageData) {
        if(imageData != null) {
            binding.sgnupLblDealershipImage.setText(getFileNameFromUri(mainFacade.getMainActivity().getApplicationContext(), imageData));
        } else {
            mainFacade.makeToast("Image selection canceled", Toast.LENGTH_SHORT);
        }
    }
}
