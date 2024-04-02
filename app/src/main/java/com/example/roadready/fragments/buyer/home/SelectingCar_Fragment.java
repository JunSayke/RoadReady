package com.example.roadready.fragments.buyer.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.data.DealershipGson;
import com.example.roadready.classes.model.gson.data.VehicleGson;
import com.example.roadready.classes.model.gson.response.ErrorGson;
import com.example.roadready.classes.model.gson.response.ResponseGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.databinding.FragmentSelectingCarBinding;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectingCar_Fragment extends Fragment {
    private final String TAG = "SelectingCar_Fragment";
    private FragmentSelectingCarBinding binding;
    private MainFacade mainFacade;
    private String modelId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectingCarBinding.inflate(inflater, container, false);
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

        modelId = SelectingCar_FragmentArgs.fromBundle(getArguments()).getModelId();
        mainFacade.getServer().getRetrofitService().getListings(modelId, null, null)
                .enqueue(getListingCallback);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private final Callback<SuccessGson<ListingsDataGson>> getListingCallback = new Callback<SuccessGson<ListingsDataGson>>() {
        @Override
        public void onResponse(@NonNull Call<SuccessGson<ListingsDataGson>> call, @NonNull Response<SuccessGson<ListingsDataGson>> response) {
            ResponseGson body = null;
            try {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    body = response.body();
                    Log.d(TAG, String.valueOf(body));
                    ListingsDataGson data = (ListingsDataGson) ((SuccessGson<?>) body).getData();
                    updateVehicleInfo(data.getListing());
                } else {
                    assert response.errorBody() != null;
                    body = new Gson().fromJson(response.errorBody().string(), ErrorGson.class);
                    Log.e(TAG, String.valueOf(body));
                }
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            } finally {
                if (body != null) {
                    mainFacade.makeToast(body.getMessage(), Toast.LENGTH_SHORT);
                }
            }
        }

        @Override
        public void onFailure(@NonNull Call<SuccessGson<ListingsDataGson>> call, @NonNull Throwable t) {
            Log.e(TAG, "Fetching Listing Failed!" + t.getMessage());
            mainFacade.makeToast("Network Error!", Toast.LENGTH_SHORT);
        }
    };

    private void updateVehicleInfo(VehicleGson vehicleGson) {
        DealershipGson dealershipGson = vehicleGson.getDealershipGson();

//        Picasso.get().load(dealershipGson.getImage()).into(binding.sgcImageDealerLogo);
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
            mainFacade.getHomeNavGraphController().navigate(action);
        });

        binding.sgnBtnInstallment.setOnClickListener(v -> {
            disabledButtons();
            SelectingCar_FragmentDirections.ActionSelectingCarFragmentToInstallmentFormFragment action =
                    SelectingCar_FragmentDirections.actionSelectingCarFragmentToInstallmentFormFragment();
            action.setModelId(modelId);
            mainFacade.getHomeNavGraphController().navigate(action);
        });
    }

    private void disabledButtons() {
        binding.sgcBtnCash.setEnabled(false);
        binding.sgnBtnInstallment.setEnabled(false);
    }

    private void enabledButtons() {
        binding.sgcBtnCash.setEnabled(true);
        binding.sgnBtnInstallment.setEnabled(true);
    }
}