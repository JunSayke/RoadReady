package com.example.roadready.fragments.buyer.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.data.VehicleGson;
import com.example.roadready.classes.ui.adapter.BuyerVehicleListingsRecyclerViewAdapter;
import com.example.roadready.databinding.FragmentBuyerHomeBinding;

import java.util.ArrayList;

import java.util.List;

public class BuyerHome_Fragment extends Fragment {
    private final String TAG = "BuyerHome_Fragment";
    private FragmentBuyerHomeBinding binding;
    private MainFacade mainFacade;
    private List<VehicleGson> itemList;
    private List<VehicleGson> activeItemList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBuyerHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        itemList = new ArrayList<>();
        activeItemList = new ArrayList<>();

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

        final RoadReadyServer.ResponseListener<ListingsDataGson> responseListener = new RoadReadyServer.ResponseListener<ListingsDataGson>() {
            @Override
            public void onSuccess(ListingsDataGson data) {
                for(VehicleGson vehicleGson : data.getListings()) {
                    if(vehicleGson.isAvailable()) {
                        itemList.add(vehicleGson);
                    }
                }
                initSearchBar();
                updateScrollViewItems(itemList);
                binding.bhSVItems.setLayoutManager(new LinearLayoutManager(mainFacade.getMainActivity().getApplicationContext()));
            }

            @Override
            public void onFailure(String message) {
                mainFacade.makeToast(message, Toast.LENGTH_SHORT);
            }
        };

        mainFacade.getListings(responseListener, null, null, null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initSearchBar() {
        binding.bhInptSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activeItemList.clear();
                for(VehicleGson items : itemList) {
                    if(items.getModelAndName().toLowerCase().contains(s.toString().toLowerCase())) {
                        activeItemList.add(items);
                    }
                }

                updateScrollViewItems(activeItemList);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void updateScrollViewItems(List<VehicleGson> data) {
        binding.bhSVItems.setAdapter(new BuyerVehicleListingsRecyclerViewAdapter(
                mainFacade.getMainActivity().getApplicationContext(),
                data, itemId -> {
            BuyerHome_FragmentDirections.ActionBuyerHomepageFragmentToSelectingCarFragment action =
                                    BuyerHome_FragmentDirections.actionBuyerHomepageFragmentToSelectingCarFragment();
                            action.setModelId(itemId);
                            mainFacade.getBuyerHomeNavController().navigate(action);
            }
        ));
    }
}