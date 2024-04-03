package com.example.roadready.fragments.buyer.applicationfragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.FragmentApplicationProgressBinding;

public class ApplicationProgress_Fragment extends Fragment {
    private final String TAG = "ApplicationProgress_Fragment";
    private FragmentApplicationProgressBinding binding;
    private MainFacade mainFacade;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentApplicationProgressBinding.inflate(inflater, container, false);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initActions() {
        binding.apBtnRegistrationProgress.setOnClickListener(v -> {
            mainFacade.getApplicationNavGraphController().navigate(R.id.action_applicationProgress_Fragment_to_vehicleRegistrationProgress_Fragment);
        });

        binding.apBtnVehicleAppProgress.setOnClickListener(v -> {
            mainFacade.getApplicationNavGraphController().navigate(R.id.action_applicationProgress_Fragment_to_vehicleApplicationProgress_Fragment);
        });
    }
}