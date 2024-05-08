package com.example.roadready.fragments.buyer.applicationfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.ApplicationsDataGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.classes.ui.adapter.BuyerApplicationListingsRecyclerViewAdapter;
import com.example.roadready.databinding.FragmentBuyerApplicationProgressBinding;

public class ApplicationProgress_Fragment extends Fragment {
    private FragmentBuyerApplicationProgressBinding binding;
    private MainFacade mainFacade;
    private int applicationCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerApplicationProgressBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        applicationCount = 0;

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        mainFacade.hideProgressBar();
        mainFacade.hideBackDrop();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainFacade.showProgressBar();
        final RoadReadyServer.ResponseListener<ApplicationsDataGson> responseListener = new RoadReadyServer.ResponseListener<ApplicationsDataGson>() {
            @Override
            public void onSuccess(SuccessGson<ApplicationsDataGson> response) {
                binding.aplContainerApplicationList.setAdapter(new BuyerApplicationListingsRecyclerViewAdapter(
                        mainFacade.getMainActivity().getApplicationContext(),
                        response.getData().getApplications(),
                        itemId -> {
                            ApplicationProgress_FragmentDirections.ActionApplicationProgressFragmentToVehicleApplicationProgressFragment action =
                                    ApplicationProgress_FragmentDirections.actionApplicationProgressFragmentToVehicleApplicationProgressFragment();
                            action.setModelId(itemId);
                            mainFacade.getBuyerApplicationNavController().navigate(action);
                        }));
                binding.aplContainerApplicationList.setLayoutManager(new LinearLayoutManager(mainFacade.getMainActivity().getApplicationContext()));
                applicationCount = response.getData().getApplications().size();
                setApplicationCount();
                mainFacade.hideProgressBar();
            }

            @Override
            public void onFailure(int code, String message) {
                setApplicationCount();
                mainFacade.hideProgressBar();
            }
        };

        mainFacade.getBuyerApplications(responseListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setApplicationCount(){
        if (binding != null) {
            if(applicationCount <= 0){
                binding.aplApplicationCount.setVisibility(View.VISIBLE);
            }
        }
    }
}