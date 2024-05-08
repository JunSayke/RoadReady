package com.example.roadready.fragments.buyer.myvehiclefragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roadready.classes.general.ImagePicker;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.FragmentBuyerRenewalRegistrationBinding;

public class RenewalRegistration_Fragment extends Fragment {
    private enum RenewalRegistrationImage {
        MVIS, INSURANCE, COR, OR, POP
    }
    private final String TAG = "RenewalRegistration_Fragment";
    private FragmentBuyerRenewalRegistrationBinding binding;
    private MainFacade mainFacade;
    private ActivityResultLauncher<Intent> mapResultLauncher;
    private ImagePicker imagePicker;
    private Uri mvisImageData;
    private Uri insuranceImageData;
    private Uri corImageData;
    private Uri orImageData;
    private Uri popImageData;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerRenewalRegistrationBinding.inflate(inflater, container, false);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    private void initActions() {
        binding.rorBtnSubmit.setOnClickListener(v -> {
            mainFacade.makeToast("Coming Soon", Toast.LENGTH_SHORT);
        });
    }

    private void initImagePicker() {
        imagePicker = new ImagePicker(mainFacade.getMainActivity().getActivityResultRegistry());
        getLifecycle().addObserver(imagePicker);
    }

    public void onImageSelected(Uri uri, RenewalRegistrationImage imageData) {
        if(uri != null) {
            switch(imageData) {
                case MVIS:
                    mvisImageData = uri;
                    break;
                case INSURANCE:
                    insuranceImageData = uri;
                    break;
                case COR:
                    corImageData = uri;
                    break;
                case OR:
                    orImageData = uri;
                    break;
                case POP:
                    popImageData = uri;
                    break;
            }
        } else {
            mainFacade.makeToast("Image selection canceled", Toast.LENGTH_SHORT);
        }
    }
}