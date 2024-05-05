package com.example.roadready.fragments.buyer.home;

import static com.example.roadready.classes.util.GetFileNameFromUri.getFileNameFromUri;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.roadready.R;
import com.example.roadready.classes.general.FileUtils;
import com.example.roadready.classes.general.ImagePicker;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.ApplicationDataGson;
import com.example.roadready.databinding.FragmentBuyerCashPaymentFormBinding;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Objects;

public class CashPaymentForm_Fragment extends Fragment implements ImagePicker.OnImageSelectedListener {
    private final String TAG = "CashPaymentForm_Fragment";
    private FragmentBuyerCashPaymentFormBinding binding;
    private MainFacade mainFacade;
    private ImagePicker imagePicker;
    private Uri imageData;
    private File imageId;
    private String modelId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerCashPaymentFormBinding.inflate(inflater, container, false);
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
        modelId = CashPaymentForm_FragmentArgs.fromBundle(getArguments()).getModelId();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initActions() {
        binding.cpfBtnSubmit.setOnClickListener(v -> {
            submitApplication();
            //mainFacade.makeToast("Coming Soon", Toast.LENGTH_SHORT);
        });

        binding.cpfBtnCancel.setOnClickListener(v -> {
            mainFacade.getBuyerHomeNavController().popBackStack();
        });

        binding.cpfBtnUploadValidId.setOnClickListener(v -> {
            imagePicker.selectImage(this);
        });
    }

    private void submitApplication(){
        showProgressBar();

        String firstName = String.valueOf(binding.cpfInptFname.getText());
        String lastName = String.valueOf(binding.cpfInptLname.getText());
        String address = String.valueOf(binding.cpfInptAddress.getText());
        String phoneNumber = String.valueOf(binding.cpfInptPhoneNumber.getText());
        File validIdImage = FileUtils.uriToFile(mainFacade.getMainActivity().getApplicationContext(), imageData);
        File signatureImage = getSignature();
        String cashModeOfPayment = getModeOfPayment();

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
        mainFacade.applyCashForListing(responseListener, modelId, firstName, lastName, address, phoneNumber, validIdImage, signatureImage, cashModeOfPayment);
    }

    private void showProgressBar() {
        binding.cpfBtnSubmit.setEnabled(false);
        mainFacade.showProgressBar();
    }

    private void hideProgressBar() {
        binding.cpfBtnSubmit.setEnabled(true);
        mainFacade.hideProgressBar();
    }

    private String getModeOfPayment(){
        if(binding.cpfRgMop.getCheckedRadioButtonId() != -1){
            return String.valueOf(binding.getRoot().findViewById(binding.cpfRgMop.getCheckedRadioButtonId()).getContentDescription());
        }
        return "";
    }

    public File getSignature() {
        return FileUtils.saveBitmapToFile(
                binding.cpfInptSignature.getTransparentSignatureBitmap(),
                Objects.requireNonNull(mainFacade.getUserGsonViewModel().getUserGsonLiveData().getValue()).getLastName(),
                mainFacade.getMainActivity());
    }

    private void initImagePicker() {
        imagePicker = new ImagePicker(mainFacade.getMainActivity().getActivityResultRegistry());
        getLifecycle().addObserver(imagePicker);
    }

    @Override
    public void onImageSelected(Uri uri) {
        if(uri != null) {
            binding.cpfInptValidId.setText(getFileNameFromUri(mainFacade.getMainActivity().getApplicationContext(), uri));
            imageData = uri;
        } else {
            mainFacade.makeToast("Image selection canceled", Toast.LENGTH_SHORT);
        }
    }
}