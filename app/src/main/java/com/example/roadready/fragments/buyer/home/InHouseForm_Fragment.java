package com.example.roadready.fragments.buyer.home;

import static com.example.roadready.classes.util.GetFileNameFromUri.getFileNameFromUri;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roadready.classes.general.FileUtils;
import com.example.roadready.classes.general.ImagePicker;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.ApplicationDataGson;
import com.example.roadready.databinding.FragmentBuyerInhouseFormBinding;

import java.io.File;
import java.util.Objects;

public class InHouseForm_Fragment extends Fragment implements ImagePicker.OnImageSelectedListener {
    private final String TAG = "InHouseForm_Fragment";
    private FragmentBuyerInhouseFormBinding binding;
    private MainFacade mainFacade;
    private String modelId;
    private ImagePicker imagePicker;
    private Uri imageData;
    private Uri coMakerImageData;
    private boolean isImage = false, isCoMakerImage = false;
    private File imageId;
    private File coMakerImageId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerInhouseFormBinding.inflate(inflater, container, false);
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
        modelId = InHouseForm_FragmentArgs.fromBundle(getArguments()).getModelId();
        mainFacade.makeToast(modelId, Toast.LENGTH_SHORT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initActions() {
        binding.ihBtnSubmit.setOnClickListener(v -> {
            // TODO: Handle Submit Action Event
            submitApplication();
            //mainFacade.makeToast("Coming Soon", Toast.LENGTH_SHORT);
        });

        binding.ihBtnCancel.setOnClickListener(v -> {
            mainFacade.getBuyerHomeNavController().popBackStack();
            //mainFacade.makeToast("Coming Soon", Toast.LENGTH_SHORT);
        });

        binding.ihBtnUploadValidId.setOnClickListener(v -> {
            isImage = true;
            imagePicker.selectImage(this);
        });

        binding.ihBtnUploadComakerValidId.setOnClickListener(v -> {
            isCoMakerImage = true;
            imagePicker.selectImage(this);
        });
    }

    private void submitApplication(){
        showProgressBar();

        String firstName = String.valueOf(binding.ihInptFname.getText());
        String lastName = String.valueOf(binding.ihInptLname.getText());
        String address = String.valueOf(binding.ihInptAddress.getText());
        String phoneNumber = String.valueOf(binding.ihInptPhoneNumber.getText());
        File validIdImage = FileUtils.uriToFile(mainFacade.getMainActivity().getApplicationContext(), imageData);
        File signatureImage = getSignature();
        String coMakerFirstName = String.valueOf(binding.ihInptComakerFirstName.getText());
        String coMakerLastName = String.valueOf(binding.ihInptComakerLastName.getText());
        String coMakerAddress = String.valueOf(binding.ihInptComakerAddress.getText());
        String coMakerPhoneNumber = String.valueOf(binding.ihInptComakerPhoneNumber.getText());
        File coMakerValidIdImage = FileUtils.uriToFile(mainFacade.getMainActivity().getApplicationContext(), coMakerImageData);
        File coMakerSignatureImage = getCoMakerSignature();

        RoadReadyServer.ResponseListener<ApplicationDataGson> responseListener = new RoadReadyServer.ResponseListener<ApplicationDataGson>() {
            @Override
            public void onSuccess(ApplicationDataGson data) {
                hideProgressBar();
            }

            @Override
            public void onFailure(String message) {
                mainFacade.makeToast(message, Toast.LENGTH_SHORT);
                hideProgressBar();
            }
        };
        mainFacade.applyInHouseFinanceListing(responseListener, modelId, firstName, lastName, address, phoneNumber, validIdImage, signatureImage, coMakerFirstName, coMakerLastName, coMakerAddress, coMakerPhoneNumber, coMakerValidIdImage, coMakerSignatureImage);
    }

    private void showProgressBar() {
        binding.ihBtnSubmit.setEnabled(false);
        mainFacade.showProgressBar();
    }

    private void hideProgressBar() {
        binding.ihBtnSubmit.setEnabled(true);
        mainFacade.hideProgressBar();
    }

    public File getSignature() {
        return FileUtils.saveBitmapToFile(
                binding.ihInptSignature.getTransparentSignatureBitmap(),
                Objects.requireNonNull(mainFacade.getUserGsonViewModel().getUserGsonLiveData().getValue()).getLastName(),
                mainFacade.getMainActivity());
    }

    public File getCoMakerSignature(){
        return FileUtils.saveBitmapToFile(
                binding.ihInptComakerSignature.getTransparentSignatureBitmap(),
                Objects.requireNonNull(mainFacade.getUserGsonViewModel().getUserGsonLiveData().getValue()).getLastName(),
                mainFacade.getMainActivity());
    }
    private void initImagePicker() {
        imagePicker = new ImagePicker(mainFacade.getMainActivity().getActivityResultRegistry());
        getLifecycle().addObserver(imagePicker);
    }
    @Override
    public void onImageSelected(Uri uri) {
        if(isImage){
            if(uri != null) {
                imageData = uri;
                binding.ihInptValidId.setText(getFileNameFromUri(mainFacade.getMainActivity().getApplicationContext(), uri));
            } else {
                mainFacade.makeToast("Image selection canceled", Toast.LENGTH_SHORT);
            }
        }

        if(isCoMakerImage){
            if(uri != null) {
                coMakerImageData = uri;
                binding.ihInptComakerValidId.setText(getFileNameFromUri(mainFacade.getMainActivity().getApplicationContext(), uri));
            } else {
                mainFacade.makeToast("Image selection canceled", Toast.LENGTH_SHORT);
            }
        }

        isImage = false;
        isCoMakerImage = false;
    }
}