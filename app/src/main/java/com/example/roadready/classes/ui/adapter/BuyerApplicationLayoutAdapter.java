package com.example.roadready.classes.ui.adapter;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.data.ApplicationGson;
import com.example.roadready.classes.model.gson.data.VehicleGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.fragments.buyer.myvehiclefragments.Application_Layout_Fragment;

import java.util.List;

public class BuyerApplicationLayoutAdapter extends FragmentStateAdapter {
    private final List<ApplicationGson> applications;
    private final MainFacade mainFacade;

    public BuyerApplicationLayoutAdapter(@NonNull FragmentActivity fragmentActivity, List<ApplicationGson> applications) {
        super(fragmentActivity);
        this.applications = applications;
        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        ApplicationGson application = applications.get(position);
        Application_Layout_Fragment fragment = new Application_Layout_Fragment(application.getApplicationPdfUrl());
        final RoadReadyServer.ResponseListener<ListingsDataGson> responseListener = new RoadReadyServer.ResponseListener<ListingsDataGson>() {
            @Override
            public void onSuccess(SuccessGson<ListingsDataGson> response) {
                VehicleGson vehicle = response.getData().getListing();
                fragment.setVehicleName(vehicle.getModelAndName());
                fragment.setVehicleImage(vehicle.getImageUrl());
            }

            @Override
            public void onFailure(int code, String message) {
                if (code != -1)
                    mainFacade.makeToast(message, Toast.LENGTH_SHORT);
            }
        };
        mainFacade.getListings(responseListener, application.getListingId(), null, null);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return applications.size();
    }
}
