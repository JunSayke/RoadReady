package com.example.roadready.fragments.dealership.mainnav;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.FragmentDealershipMyvehiclesContainerBinding;

public class Dealership_MyVehicles_Container_Fragment extends Fragment {
    private final String TAG = "Dealership_MyVehicle_Container_Fragment";
    private FragmentDealershipMyvehiclesContainerBinding binding;
    private MainFacade mainFacade;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDealershipMyvehiclesContainerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        mainFacade.hideProgressBar();
        mainFacade.hideBackDrop();

        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.dealership_myVehicleFragmentContainer);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        mainFacade.setDealershipMyVehicleNavController(navController);
        mainFacade.setCurrentNavController(navController);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}