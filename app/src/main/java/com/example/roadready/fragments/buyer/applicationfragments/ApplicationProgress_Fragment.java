package com.example.roadready.fragments.buyer.applicationfragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.ApplicationsDataGson;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.data.UserGson;
import com.example.roadready.classes.ui.adapter.BuyerApplicationListingsRecyclerViewAdapter;
import com.example.roadready.classes.ui.adapter.DealershipVehicleListingsRecyclerViewAdapter;
import com.example.roadready.databinding.FragmentBuyerApplicationProgressBinding;

public class ApplicationProgress_Fragment extends Fragment {
    private final String TAG = "ApplicationProgress_Fragment";
    private FragmentBuyerApplicationProgressBinding binding;
    private MainFacade mainFacade;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerApplicationProgressBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        UserGson userGson = mainFacade.getSessionManager().getUserGson();
        if(!userGson.getIsApproved()) {
//            mainFacade.restrictButton(binding.apBtnRegistrationProgress);
//            mainFacade.restrictButton(binding.apBtnVehicleAppProgress);
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RoadReadyServer.ResponseListener<ApplicationsDataGson> responseListener = new RoadReadyServer.ResponseListener<ApplicationsDataGson>() {
            @Override
            public void onSuccess(ApplicationsDataGson data) {
                Log.d(TAG, String.valueOf(data));
                binding.aplContainerApplicationList.setAdapter(new BuyerApplicationListingsRecyclerViewAdapter(
                        mainFacade.getMainActivity().getApplicationContext(),
                        data.getApplications(),
                        itemId -> {
                            ApplicationProgress_FragmentDirections.ActionApplicationProgressFragmentToVehicleApplicationProgressFragment action =
                                    ApplicationProgress_FragmentDirections.actionApplicationProgressFragmentToVehicleApplicationProgressFragment();
                            action.setModelId(itemId);
                            mainFacade.getBuyerApplicationNavController().navigate(action);
                        }));
                binding.aplContainerApplicationList.setLayoutManager(new LinearLayoutManager(mainFacade.getMainActivity().getApplicationContext()));
            }

            @Override
            public void onFailure(String message) {
                mainFacade.makeToast(message, Toast.LENGTH_SHORT);
            }
        };
        mainFacade.getBuyerApplications(responseListener);

        initActions();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initActions() {
        //TODO: Fix this pls

//        binding.apBtnRegistrationProgress.setOnClickListener(v -> {
//            mainFacade.getBuyerApplicationNavController().navigate(R.id.action_applicationProgress_Fragment_to_vehicleRegistrationProgress_Fragment);
//        })
    }
}