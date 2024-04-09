package com.example.roadready.fragments.buyer.profilefragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roadready.R;
import com.example.roadready.activity.MainActivity;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.FragmentBuyerProfileBinding;

public class BuyerProfile_Fragment extends Fragment {
    private final String TAG = "BuyerProfile_Fragment";
    private FragmentBuyerProfileBinding binding;
    private MainFacade mainFacade;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        mainFacade.getUserGsonViewModel().getUserGsonLiveData().observe(getViewLifecycleOwner(), buyerGson -> {
            String fullName = buyerGson.getFirstName() + " " + buyerGson.getLastName();
            binding.bpInptName.setText(fullName);
            binding.bpInptAddress.setText(buyerGson.getAddress());
            binding.bpInptEmail.setText(buyerGson.getEmail());
            binding.bpInptPhoneNumber.setText(buyerGson.getPhoneNumber());
            binding.bpInptSex.setText(buyerGson.getGender());

            // TODO: Longitude & Latitude
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
            mainFacade.getCommonProfileNavController().navigate(R.id.action_buyerProfile_Fragment_to_buyerEditProfile_Fragment);
        });
    }
}