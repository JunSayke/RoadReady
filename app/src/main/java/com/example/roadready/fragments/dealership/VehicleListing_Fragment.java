package com.example.roadready.fragments.dealership;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.GsonData;
import com.example.roadready.classes.model.gson.data.VehicleGson;
import com.example.roadready.databinding.FragmentDealershipVehicleListingBinding;
import com.example.roadready.fragments.buyer.home.SelectingCar_FragmentArgs;

public class VehicleListing_Fragment extends Fragment {
    private final String TAG = "VehicleListing_Fragment"; // declare TAG for each class for debugging purposes using Log.d()
    private FragmentDealershipVehicleListingBinding binding; // use View binding to avoid using too much findViewById
    private MainFacade mainFacade;
    private String modelId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        modelId = VehicleListing_FragmentArgs.fromBundle(getArguments()).getModelId();

        binding = FragmentDealershipVehicleListingBinding.inflate(inflater, container, false);
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
        binding.vlBtnBack.setOnClickListener(v -> {
                mainFacade.getDealershipMyVehicleNavController().navigate(R.id.action_vehicleListing_Fragment_to_myVehicles_Fragment);
        });

        binding.vlBtnConfirm.setOnClickListener(v -> {
            showProgressBar();
            final RoadReadyServer.ResponseListener<GsonData> responseListener = new RoadReadyServer.ResponseListener<GsonData>() {
                @Override
                public void onSuccess(GsonData data) {
                    hideProgressBar();
                }

                @Override
                public void onFailure(String message) {
                    mainFacade.makeToast(message, Toast.LENGTH_SHORT);
                    hideProgressBar();
                }
            };
            mainFacade.deleteListing(responseListener, modelId);
            mainFacade.getDealershipMyVehicleNavController().navigate(R.id.action_vehicleListing_Fragment_to_myVehicles_Fragment);
        });
    }

    private void showProgressBar() {
        binding.vlBtnConfirm.setEnabled(false);
        mainFacade.showProgressBar();
    }

    private void hideProgressBar() {
        binding.vlBtnConfirm.setEnabled(true);
        mainFacade.hideProgressBar();
    }
}