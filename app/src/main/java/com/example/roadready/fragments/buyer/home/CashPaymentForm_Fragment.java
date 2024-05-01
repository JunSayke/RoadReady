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

import com.example.roadready.classes.general.FileUtils;
import com.example.roadready.classes.general.ImagePicker;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.FragmentBuyerCashPaymentFormBinding;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Objects;

public class CashPaymentForm_Fragment extends Fragment implements ImagePicker.OnImageSelectedListener {
    private final String TAG = "CashPaymentForm_Fragment";
    private FragmentBuyerCashPaymentFormBinding binding;
    private MainFacade mainFacade;
    private ImagePicker imagePicker;
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
        mainFacade.makeToast(modelId, Toast.LENGTH_SHORT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initActions() {
        binding.cpfBtnSubmit.setOnClickListener(v -> {
            // TODO: Handle Submit Action Event
            mainFacade.makeToast("Currently under construction!", Toast.LENGTH_SHORT);
        });

        binding.cpfBtnCancel.setOnClickListener(v -> {
            // TODO: Handle Cancel Action Event
            mainFacade.makeToast("Currently under construction!", Toast.LENGTH_SHORT);
        });

        binding.cpfBtnUploadValidId.setOnClickListener(v -> {
            imagePicker.selectImage(this);
        });
    }

    private void initImagePicker() {
        imagePicker = new ImagePicker(mainFacade.getMainActivity().getActivityResultRegistry());
        getLifecycle().addObserver(imagePicker);
    }

    @Override
    public void onImageSelected(Uri imageData) {
        if(imageData != null) {
            binding.cpfInptValidId.setText(getFileNameFromUri(mainFacade.getMainActivity().getApplicationContext(), imageData));
            imageId = FileUtils.uriToFile(mainFacade.getMainActivity().getApplicationContext(), imageData);
        } else {
            mainFacade.makeToast("Image selection canceled", Toast.LENGTH_SHORT);
        }
    }

    public File getImageId() {
        return imageId;
    }

    public File getSignature() {
        return FileUtils.saveBitmapToFile(
                binding.cpfInptSignature.getTransparentSignatureBitmap(),
                Objects.requireNonNull(mainFacade.getUserGsonViewModel().getUserGsonLiveData().getValue()).getLastName());
    }


}