package com.example.roadready.fragments.dealership.mainnav;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.FragmentDealershipHomepageContainerBinding;
import com.example.roadready.databinding.FragmentDealershipMyvehicleContainerBinding;

public class Dealership_MyVehicle_Container_Fragment extends Fragment {
    private final String TAG = "Dealership_MyVehicle_Container_Fragment";
    private FragmentDealershipMyvehicleContainerBinding binding;
    private MainFacade mainFacade;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDealershipMyvehicleContainerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return root;
    }
}