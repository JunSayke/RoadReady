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
import com.example.roadready.databinding.FragmentBuyerBankLoanFormBinding;

import java.io.File;
import java.util.Objects;

public class BankLoanForm_Fragment extends Fragment implements ImagePicker.OnImageSelectedListener {
    private final String TAG = "InHouseForm_Fragment";
    private FragmentBuyerBankLoanFormBinding binding;
    private MainFacade mainFacade;
    private String modelId;
    private ImagePicker imagePicker;
    private Uri imageData;
    private Uri bankImageData;
    private boolean isImage = false, isBankCertificateImage = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerBankLoanFormBinding.inflate(inflater, container, false);
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
        binding.blBtnSubmit.setOnClickListener(v -> {
            // TODO: Handle Submit Action Event
            submitApplication();
            //mainFacade.makeToast("Coming Soon", Toast.LENGTH_SHORT);
        });

        binding.blBtnCancel.setOnClickListener(v -> {
            mainFacade.getBuyerHomeNavController().popBackStack();
            //mainFacade.makeToast("Coming Soon", Toast.LENGTH_SHORT);
        });

        binding.blBtnUploadValidId.setOnClickListener(v -> {
            isImage = true;
            imagePicker.selectImage(this);
        });

        binding.blBtnUploadBankCertificate.setOnClickListener(v -> {
            isBankCertificateImage = true;
            imagePicker.selectImage(this);
        });

        binding.blRbBuyerChoice.setOnClickListener(v -> {
            buyerChoiceCheck();
        });

        binding.blRbDealershipChoice.setOnClickListener(v -> {
            buyerChoiceCheck();
        });
    }

    void buyerChoiceCheck(){
        if(binding.blRbBuyerChoice.isChecked()){
            binding.blContainerBankCertificate.setVisibility(View.VISIBLE);
        }else{
            binding.blContainerBankCertificate.setVisibility(View.GONE);
        }
    }

    private void submitApplication(){
        showProgressBar();

        String firstName = String.valueOf(binding.blInptFname.getText());
        String lastName = String.valueOf(binding.blInptLname.getText());
        String address = String.valueOf(binding.blInptAddress.getText());
        String phoneNumber = String.valueOf(binding.blInptPhoneNumber.getText());
        File validIdImage = FileUtils.uriToFile(mainFacade.getMainActivity().getApplicationContext(), imageData);
        File signatureImage = getSignature();
        File bankCertifcateImage = FileUtils.uriToFile(mainFacade.getMainActivity().getApplicationContext(), bankImageData);

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
        if(binding.blRgPickBank.getCheckedRadioButtonId() != -1){
            String bankChoice = String.valueOf(binding.getRoot().findViewById(binding.blRgPickBank.getCheckedRadioButtonId()).getContentDescription());
            if(bankChoice.equals("bankLoan(dealershipBankChoice)")){
                mainFacade.makeToast("bankLoan(dealershipBankChoice)", Toast.LENGTH_SHORT);
                mainFacade.applyBankLoanDealershipBankChoiceListing(responseListener, modelId, firstName, lastName, address, phoneNumber, validIdImage, signatureImage);
            }else{
                mainFacade.makeToast("bankLoan(buyerBankChoice)", Toast.LENGTH_SHORT);
                mainFacade.applyBankLoanBuyerBankChoiceListing(responseListener, modelId, firstName, lastName, address, phoneNumber, validIdImage, signatureImage, bankCertifcateImage);
            }
        }else{
            mainFacade.makeToast("Pick a Bank", Toast.LENGTH_SHORT);
        }
    }

    private void showProgressBar() {
        binding.blBtnSubmit.setEnabled(false);
        mainFacade.showProgressBar();
    }

    private void hideProgressBar() {
        binding.blBtnSubmit.setEnabled(true);
        mainFacade.hideProgressBar();
    }

    public File getSignature() {
        return FileUtils.saveBitmapToFile(
                binding.blInptSignature.getTransparentSignatureBitmap(),
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
                binding.blInptValidId.setText(getFileNameFromUri(mainFacade.getMainActivity().getApplicationContext(), uri));
            } else {
                mainFacade.makeToast("Image selection canceled", Toast.LENGTH_SHORT);
            }
        }

        if(isBankCertificateImage){
            if(uri != null) {
                bankImageData = uri;
                binding.blInptBankCertificate.setText(getFileNameFromUri(mainFacade.getMainActivity().getApplicationContext(), uri));
            } else {
                mainFacade.makeToast("Image selection canceled", Toast.LENGTH_SHORT);
            }
        }

        isImage = false;
        isBankCertificateImage = false;
    }
}