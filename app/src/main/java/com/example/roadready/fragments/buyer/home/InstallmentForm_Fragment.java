package com.example.roadready.fragments.buyer.home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.roadready.classes.general.FileUtils;
import com.example.roadready.classes.general.ImagePicker;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.FragmentBuyerInstallmentFormBinding;

import java.io.File;
import java.util.Objects;

public class InstallmentForm_Fragment extends Fragment implements ImagePicker.OnImageSelectedListener {
    private final String TAG = "InstallmentForm_Fragment";
    private FragmentBuyerInstallmentFormBinding binding;
    private MainFacade mainFacade;
    private String modelId;

    private ImagePicker imagePicker;
    private File imageId;
    private File coMakerImageId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerInstallmentFormBinding.inflate(inflater, container, false);
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
        modelId = InstallmentForm_FragmentArgs.fromBundle(getArguments()).getModelId();
        mainFacade.makeToast(modelId, Toast.LENGTH_SHORT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initActions() {
        binding.instBtnSubmit.setOnClickListener(v -> {
            // TODO: Handle Submit Action Event
            submitApplication();
            mainFacade.makeToast("Currently under construction!", Toast.LENGTH_SHORT);
        });

        binding.instBtnCancel.setOnClickListener(v -> {
            // TODO: Handle Cancel Action Event
            mainFacade.getBuyerHomeNavController().popBackStack();
            //mainFacade.makeToast("Currently under construction!", Toast.LENGTH_SHORT);
        });
    }

    private void submitApplication(){
        showProgressBar();

        String firstName = String.valueOf(binding.instInptFname.getText());
        String lastName = String.valueOf(binding.instInptLname.getText());
        String address = String.valueOf(binding.instInptAddress.getText());
        String phoneNumber = String.valueOf(binding.instInptPhoneNumber.getText());
        File validIdImage = imageId;
        File signatureImage = getSignature();
        String coMakerFirstName = String.valueOf(binding.instInptComakerFirstName.getText());
        String coMakerLastName = String.valueOf(binding.instInptComakerLastName.getText());
        String coMakerAddress = String.valueOf(binding.instInptComakerAddress.getText());
        String coMakerPhoneNumber = String.valueOf(binding.instInptComakerPhoneNumber.getText());
        File coMakerValidIdImage = imageId;
        File coMakerSignatureImage = getCoMakerSignature();
    }

    private void showProgressBar() {
        binding.instBtnSubmit.setEnabled(false);
        mainFacade.showProgressBar();
    }

    private void hideProgressBar() {
        binding.instBtnSubmit.setEnabled(true);
        mainFacade.hideProgressBar();
    }

    public File getSignature() {
        return FileUtils.saveBitmapToFile(
                binding.instInptSignature.getTransparentSignatureBitmap(),
                Objects.requireNonNull(mainFacade.getUserGsonViewModel().getUserGsonLiveData().getValue()).getLastName());
    }

    public File getCoMakerSignature(){
        return FileUtils.saveBitmapToFile(
                binding.instInptComakerSignature.getTransparentSignatureBitmap(),
                Objects.requireNonNull(mainFacade.getUserGsonViewModel().getUserGsonLiveData().getValue()).getLastName());
    }

    @Override
    public void onImageSelected(Uri imageData) {

    }
}