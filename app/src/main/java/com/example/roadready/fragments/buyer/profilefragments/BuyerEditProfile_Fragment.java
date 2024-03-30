package com.example.roadready.fragments.buyer.profilefragments;

import static com.example.roadready.classes.util.GetFileNameFromUri.getFileNameFromUri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.roadready.R;
import com.example.roadready.activity.GoogleMaps_Activity;
import com.example.roadready.classes.general.ImagePicker;
import com.example.roadready.classes.util.GetFileNameFromUri;
import com.example.roadready.databinding.FragmentBuyerEditProfileBinding;

import java.util.Objects;

public class BuyerEditProfile_Fragment extends Fragment implements ImagePicker.OnImageSelectedListener {

    private final String TAG = "BuyerEditProfile_Activity"; // Declare TAG for each class for debugging purposes using Log.d()
    private FragmentBuyerEditProfileBinding binding; // Use View binding to avoid using too much findViewById
    private ActivityResultLauncher<Intent> mapResultLauncher;
    private NavController navController;
    private ImagePicker imagePicker;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerEditProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        navController = Navigation.findNavController(requireActivity(), R.id.profileFragmentContainer);

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
    }

    private void startGoogleMaps() {
        Intent intent = new Intent(requireContext(), GoogleMaps_Activity.class);
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
        imagePicker = new ImagePicker(requireActivity().getActivityResultRegistry());
        getLifecycle().addObserver(imagePicker);
    }

    @Override
    public void onImageSelected(Uri imageData) {
        if(imageData != null) {
            binding.bepImageUserIcon.setImageURI(imageData);
            binding.bepInptValidId.setText(getFileNameFromUri(requireContext(), imageData));
        } else {
            Toast.makeText(requireContext(), "Image selection canceled", Toast.LENGTH_SHORT).show();
        }
    }
}