package com.example.roadready.fragments.buyer.profilefragments;

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
import com.example.roadready.classes.model.gson.UserDataGson;
import com.example.roadready.classes.model.gson.data.UserGson;
import com.example.roadready.databinding.FragmentBuyerEditProfileBinding;

import java.io.File;

public class BuyerEditProfile_Fragment extends Fragment implements ImagePicker.OnImageSelectedListener {
    private final String TAG = "BuyerEditProfile_Fragment";
    private FragmentBuyerEditProfileBinding binding;
    private ActivityResultLauncher<Intent> mapResultLauncher;
    private MainFacade mainFacade;
    private ImagePicker imagePicker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerEditProfileBinding.inflate(inflater, container, false);
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

        initActions();
        initMapResultLauncher();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initActions() {
        binding.bepBtnOpenMaps.setOnClickListener(v -> {
            startGoogleMaps();
        });

        binding.bepBtnUpload.setOnClickListener(v -> {
            imagePicker.selectImage(this);
        });

        binding.bepBtnSubmit.setOnClickListener(v -> {
            showProgressBar();

            String firstName = String.valueOf(binding.bepInptFname.getText());
            String lastName = String.valueOf(binding.bepInptLname.getText());
            String email = String.valueOf(binding.bepInptEmail.getText());
            String gender = "male";
            String phoneNumber = String.valueOf(binding.bepInptPhone.getText());
            String address = String.valueOf(binding.bepInptAddress.getText());

            File profileImageFile = FileUtils.drawableToFile(mainFacade.getMainActivity(), R.drawable.hp_iv_civic, "profile_image.png");
            // TODO: Validations, Longitude and Latitude, Valid ID

            final RoadReadyServer.ResponseListener<UserDataGson> responseListener = new RoadReadyServer.ResponseListener<UserDataGson>() {
                @Override
                public void onSuccess(UserDataGson data) {
                    UserGson user = data.getUserGson();
                    mainFacade.getUserGsonViewModel().setUserGsonLiveData(user);
                    mainFacade.getSessionManager().setUserGson(user);
                    hideProgressBar();
                }

                @Override
                public void onFailure(String message) {
                    mainFacade.makeToast(message, Toast.LENGTH_SHORT);
                    hideProgressBar();
                }
            };

            mainFacade.updateBuyerProfile(responseListener, profileImageFile, firstName, lastName, phoneNumber, gender, address);
        });

        binding.bepBtnCancel.setOnClickListener(v -> {
            mainFacade.makeToast("Currently under construction!", Toast.LENGTH_SHORT);
        });
    }

    private void showProgressBar() {
        binding.bepBtnSubmit.setEnabled(false);
        mainFacade.showProgressBar();
    }

    private void hideProgressBar() {
        binding.bepBtnSubmit.setEnabled(true);
        mainFacade.hideProgressBar();
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
                    binding.bepInptCoordinates.setText(LongLatText);
                }
            }
        });
    }

    private void initImagePicker() {
        imagePicker = new ImagePicker(mainFacade.getMainActivity().getActivityResultRegistry());
        getLifecycle().addObserver(imagePicker);
    }

    @Override
    public void onImageSelected(Uri imageData) {
        if(imageData != null) {
            binding.bepImageUserIcon.setImageURI(imageData);
            binding.bepInptValidId.setText(getFileNameFromUri(mainFacade.getMainActivity().getApplicationContext(), imageData));
        } else {
            mainFacade.makeToast("Image selection canceled", Toast.LENGTH_SHORT);
        }
    }
}