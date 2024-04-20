package com.example.roadready.fragments.buyer.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.data.DealershipGson;
import com.example.roadready.classes.model.gson.data.UserGson;
import com.example.roadready.classes.model.gson.data.VehicleGson;
import com.example.roadready.databinding.FragmentBuyerSelectingCarBinding;
import com.squareup.picasso.Picasso;

public class SelectingCar_Fragment extends Fragment {
    private final String TAG = "SelectingCar_Fragment";
    private FragmentBuyerSelectingCarBinding binding;
    private MainFacade mainFacade;
    private String modelId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerSelectingCarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        UserGson userGson = mainFacade.getSessionManager().getUserGson();
        if(!userGson.isApproved()) {
            mainFacade.restrictButton(binding.sgcBtnCash);
            mainFacade.restrictButton(binding.sgcBtnInstallment);
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        modelId = SelectingCar_FragmentArgs.fromBundle(getArguments()).getModelId();

        final RoadReadyServer.ResponseListener<ListingsDataGson> responseListener = new RoadReadyServer.ResponseListener<ListingsDataGson>() {
            @Override
            public void onSuccess(ListingsDataGson data) {
                updateVehicleInfo(data.getListings().get(0));
            }

            @Override
            public void onFailure(String message) {
                mainFacade.makeToast(message, Toast.LENGTH_SHORT);
            }
        };

        mainFacade.getListings(responseListener, modelId, null, null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateVehicleInfo(VehicleGson vehicleGson) {
        DealershipGson dealershipGson = vehicleGson.getDealershipGson();

        Picasso.get().load(dealershipGson.getImage()).into(binding.sgcImageDealerLogo);
        binding.sgcTextDealerName.setText(dealershipGson.getName());
        Picasso.get().load(vehicleGson.getImage()).into(binding.sgcImageItem);
        binding.sgcTextItemName.setText(vehicleGson.getModelAndName());
        binding.sgcInptMake.setText(vehicleGson.getBrand());
        binding.sgcInptFuelType.setText(vehicleGson.getFuelType());
        binding.sgcInptTransmission.setText(vehicleGson.getTransmission());
        binding.sgcInptFtc.setText(vehicleGson.getFuelTankCapacity());
        binding.sgcInptSc.setText(vehicleGson.getSeatingCapacity());
        binding.sgcInptPrice.setText(String.valueOf(vehicleGson.getPrice()));
        binding.sgcInptPower.setText(vehicleGson.getPower());
        binding.sgcInptEngine.setText(vehicleGson.getEngine());

        // TODO: Handle cash and installment click events
        binding.sgcBtnCash.setOnClickListener(v -> {
            disabledButtons();
            SelectingCar_FragmentDirections.ActionSelectingCarFragmentToCashPaymentFormFragment action =
                    SelectingCar_FragmentDirections.actionSelectingCarFragmentToCashPaymentFormFragment();
            action.setModelId(modelId);
            mainFacade.getBuyerHomeNavController().navigate(action);
        });

        binding.sgcBtnInstallment.setOnClickListener(v -> {
            disabledButtons();
            SelectingCar_FragmentDirections.ActionSelectingCarFragmentToInstallmentFormFragment action =
                    SelectingCar_FragmentDirections.actionSelectingCarFragmentToInstallmentFormFragment();
            action.setModelId(modelId);
            mainFacade.getBuyerHomeNavController().navigate(action);
        });
    }

    private void disabledButtons() {
        binding.sgcBtnCash.setEnabled(false);
        binding.sgcBtnInstallment.setEnabled(false);
    }

    private void enabledButtons() {
        binding.sgcBtnCash.setEnabled(true);
        binding.sgcBtnInstallment.setEnabled(true);
    }
}