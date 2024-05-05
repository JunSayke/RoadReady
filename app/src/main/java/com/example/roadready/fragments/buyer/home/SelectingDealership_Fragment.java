package com.example.roadready.fragments.buyer.home;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.DealershipsDataGson;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.data.DealershipGson;
import com.example.roadready.classes.model.gson.data.VehicleGson;
import com.example.roadready.classes.ui.adapter.BuyerVehicleListingsRecyclerViewAdapter;
import com.example.roadready.databinding.FragmentBuyerSelectingDealershipBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SelectingDealership_Fragment extends Fragment {
    private final String TAG = "SelectingDealership_Fragment";
    private FragmentBuyerSelectingDealershipBinding binding;
    private MainFacade mainFacade;
    private String dealershipId;
    private Location currentLocation;
    private List listingList;
    private DealershipGson dealershipGson;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerSelectingDealershipBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listingList = new ArrayList<>();

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        mainFacade.fetchLocation(location -> currentLocation = location);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainFacade.showProgressBar();
        mainFacade.showBackDrop();
        binding.getRoot().setVisibility(View.INVISIBLE);

        dealershipId = SelectingDealership_FragmentArgs.fromBundle(getArguments()).getDealershipId();

        final RoadReadyServer.ResponseListener<DealershipsDataGson> dealershipResponseListener = new RoadReadyServer.ResponseListener<DealershipsDataGson>() {
            @Override
            public void onSuccess(DealershipsDataGson data) {
                for(DealershipGson dealership : data.getDealerships()){
                    if(dealership.getId().equals(dealershipId)){
                        dealershipGson = dealership;
                        break;
                    }
                }
                initDealership();
                mainFacade.hideProgressBar();
                mainFacade.hideBackDrop();
                binding.getRoot().setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(String message) {
                mainFacade.makeToast(message, Toast.LENGTH_SHORT);
                mainFacade.hideProgressBar();
                mainFacade.hideBackDrop();
                binding.getRoot().setVisibility(View.VISIBLE);
            }
        };
        mainFacade.getDealerships(dealershipResponseListener, null, null);

        final RoadReadyServer.ResponseListener<ListingsDataGson> responseListener = new RoadReadyServer.ResponseListener<ListingsDataGson>() {
            @Override
            public void onSuccess(ListingsDataGson data) {
                for(VehicleGson vehicle : data.getListings()) {
                    listingList.add(vehicle);
                }
                binding.sgdSVItems.setAdapter(new BuyerVehicleListingsRecyclerViewAdapter(
                            binding.getRoot().getContext(), currentLocation, listingList,
                            itemId -> {
                                SelectingDealership_FragmentDirections.ActionSelectingDealershipFragmentToSelectingCarFragment action =
                                        SelectingDealership_FragmentDirections.actionSelectingDealershipFragmentToSelectingCarFragment();
                                action.setModelId(itemId);
                                mainFacade.getBuyerHomeNavController().navigate(action);
                            }
                        ));
                binding.sgdSVItems.setLayoutManager(new LinearLayoutManager(mainFacade.getMainActivity().getApplicationContext()));
            }

            @Override
            public void onFailure(String message) {
                mainFacade.makeToast(message, Toast.LENGTH_SHORT);
            }
        };
        mainFacade.getListings(responseListener, null, dealershipId, null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initDealership(){
        Picasso.get().load(dealershipGson.getDealershipImageUrl()).into(binding.sgdLogoDealer);
        binding.sgdTextDealerName.setText(dealershipGson.getName());
    }
}