package com.example.roadready.fragments.dealership.profilefragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roadready.R;
import com.example.roadready.activity.MainActivity;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.FragmentDealershipProfileBinding;

public class Dealership_Profile_Fragment extends Fragment {
    private final String TAG = "Profile_Fragment";
    private FragmentDealershipProfileBinding binding;
    private MainFacade mainFacade;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDealershipProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        mainFacade.getUserGsonViewModel().getUserGsonLiveData().observe(getViewLifecycleOwner(), dealershipGson -> {
            String fullName = dealershipGson.getFirstName() + " " + dealershipGson.getLastName();
            binding.bpInptName.setText(fullName);
            binding.bpInptAddress.setText(dealershipGson.getAddress());
            binding.bpInptEmail.setText(dealershipGson.getEmail());
            binding.bpInptPhoneNumber.setText(dealershipGson.getPhoneNumber());
            binding.bpInptSex.setText(dealershipGson.getGender());
            binding.bpInptLongitudeId.setText(dealershipGson.getDealership().getLongitude());
            binding.bpInptLatitudeId.setText(dealershipGson.getDealership().getLatitude());
        });

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
        binding.bpBtnLogout.setOnClickListener(v -> {
            mainFacade.stopLoginSession();
            startActivity(new Intent(mainFacade.getMainActivity().getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });

        binding.bpBtnEditProfile.setOnClickListener(v -> {
            mainFacade.getCommonProfileNavController().navigate(R.id.action_dealershipEditProfile_Fragment_to_dealershipProfile_Fragment);
        });
    }
}