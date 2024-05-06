package com.example.roadready.fragments.buyer.profilefragments;

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

import com.example.roadready.R;
import com.example.roadready.classes.general.FileUtils;
import com.example.roadready.classes.general.ImagePicker;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.UserDataGson;
import com.example.roadready.classes.model.gson.data.UserGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.classes.util.CircleTransform;
import com.example.roadready.databinding.FragmentBuyerEditProfileBinding;
import com.squareup.picasso.Picasso;

import java.io.File;

public class Buyer_EditProfile_Fragment extends Fragment implements ImagePicker.OnImageSelectedListener {
    private final String TAG = "EditProfile_Fragment";
    private FragmentBuyerEditProfileBinding binding;
    private MainFacade mainFacade;
    private ImagePicker imagePicker;
    private Uri imageData;

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

        mainFacade.getUserGsonViewModel().getUserGsonLiveData().observe(getViewLifecycleOwner(), buyerGson -> {
            Picasso.get()
                    .load(buyerGson.getProfileImageUrl())
                    .transform(new CircleTransform())
                    .placeholder(R.drawable.default_user_icon)
                    .error(R.drawable.app_ib_cancel)
                    .into(binding.bepImageUserIcon);
        });
        initImagePicker();

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
        binding.bepBtnUpload.setOnClickListener(v -> {
            imagePicker.selectImage(this);
        });

        binding.bepBtnSubmit.setOnClickListener(v -> {
            showProgressBar();

            String firstName = String.valueOf(binding.bepInptFname.getText());
            String lastName = String.valueOf(binding.bepInptLname.getText());
            String email = String.valueOf(binding.bepInptEmail.getText());
            String gender = null;
            String phoneNumber = String.valueOf(binding.bepInptPhone.getText());
            String address = String.valueOf(binding.bepInptAddress.getText());
            File imageFile = FileUtils.uriToFile(mainFacade.getMainActivity().getApplicationContext(), imageData);

            final RoadReadyServer.ResponseListener<UserDataGson> responseListener = new RoadReadyServer.ResponseListener<UserDataGson>() {
                @Override
                public void onSuccess(SuccessGson<UserDataGson> response) {
                    UserGson user = response.getData().getUserGson();
                    mainFacade.makeToast(response.getMessage(), Toast.LENGTH_SHORT);
                    mainFacade.getUserGsonViewModel().setUserGsonLiveData(user);
                    mainFacade.getSessionManager().setUserGson(user);
                    hideProgressBar();
                }

                @Override
                public void onFailure(int code, String message) {
                    if (code != -1)
                        mainFacade.makeToast(message, Toast.LENGTH_SHORT);
                    hideProgressBar();
                }
            };

            mainFacade.updateBuyerProfile(responseListener, imageFile, firstName, lastName, phoneNumber, gender, address);
        });

        binding.bepBtnCancel.setOnClickListener(v -> {
            mainFacade.getMainActivity().getOnBackPressedDispatcher().onBackPressed();
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

    private void initImagePicker() {
        imagePicker = new ImagePicker(mainFacade.getMainActivity().getActivityResultRegistry());
        getLifecycle().addObserver(imagePicker);
    }

    @Override
    public void onImageSelected(Uri uri) {
        if(uri != null) {
            Picasso.get()
                    .load(uri)
                    .transform(new CircleTransform())
                    .placeholder(R.drawable.default_user_icon)
                    .error(R.drawable.app_ib_cancel)
                    .into(binding.bepImageUserIcon);
            binding.bepInptValidId.setText(getFileNameFromUri(mainFacade.getMainActivity().getApplicationContext(), uri));
            imageData = uri;
        } else {
            mainFacade.makeToast("Image selection canceled", Toast.LENGTH_SHORT);
        }
    }
}