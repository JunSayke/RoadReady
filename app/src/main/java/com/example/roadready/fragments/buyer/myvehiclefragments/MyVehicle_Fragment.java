package com.example.roadready.fragments.buyer.myvehiclefragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.model.gson.data.UserGson;
import com.example.roadready.databinding.FragmentBuyerMyVehicleBinding;

public class MyVehicle_Fragment extends Fragment {
    private final String TAG = "MyVehicle_Fragment";
    private FragmentBuyerMyVehicleBinding binding;
    private MainFacade mainFacade;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerMyVehicleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        UserGson userGson = mainFacade.getSessionManager().getUserGson();
        if(!userGson.isApproved()) {
            mainFacade.restrictButton(binding.myvBtnRenewRegistration);
        }

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
        binding.myvBtnRenewRegistration.setOnClickListener(v -> {
            // TODO: Pass params for registration
            mainFacade.getBuyerMyVehicleNavController().navigate(R.id.action_myVehicle_Fragment_to_renewalRegistration_Fragment);
        });
    }
}