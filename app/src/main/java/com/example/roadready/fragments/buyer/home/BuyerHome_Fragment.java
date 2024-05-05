package com.example.roadready.fragments.buyer.home;

import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.data.VehicleGson;
import com.example.roadready.classes.ui.adapter.BuyerHotListingsRecyclerViewAdapter;
import com.example.roadready.classes.ui.adapter.BuyerVehicleListingsRecyclerViewAdapter;
import com.example.roadready.databinding.FragmentBuyerHomeBinding;

import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;

public class BuyerHome_Fragment extends Fragment {

    private enum ItemListingFilters {
        VEHICLE, DEALERSHIP,
    }
    private final String TAG = "BuyerHome_Fragment";
    private FragmentBuyerHomeBinding binding;
    private MainFacade mainFacade;
    private List<VehicleGson> itemList;
    private List<VehicleGson> activeItemList;
    private Location currentLocation;
    private final int HOT_VEHICLE_PRICE_THRESHOLD = 100000;

    private boolean isSpinnerVisible = false;

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

        mainFacade.fetchLocation(location -> currentLocation = location);

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
                initHotListings();
                initSearchBar();
                updateScrollViewItems(itemList);
                binding.bhSVItems.setLayoutManager(new LinearLayoutManager(mainFacade.getMainActivity().getApplicationContext()));
                initFilterButton();
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

    private void initHotListings() {
        List<VehicleGson> filteredList = new ArrayList<>();
        for (VehicleGson item : itemList) {
            if (item.getPrice() < HOT_VEHICLE_PRICE_THRESHOLD) {
                filteredList.add(item);
            }
        }

        binding.bhHSVRItems.setAdapter(new BuyerHotListingsRecyclerViewAdapter(
                mainFacade.getMainActivity().getApplicationContext(),
                filteredList, itemId -> {
            BuyerHome_FragmentDirections.ActionBuyerHomepageFragmentToSelectingCarFragment action =
                    BuyerHome_FragmentDirections.actionBuyerHomepageFragmentToSelectingCarFragment();
            action.setModelId(itemId);
            mainFacade.getBuyerHomeNavController().navigate(action);
        }
        ));
    }

    private void initSearchBar() {
        binding.bhInptSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateItemListings(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void initFilterButton() {
        binding.bhBtnFilterSearch.setOnClickListener(v -> {
            isSpinnerVisible = !isSpinnerVisible;
            toggleSpinnerFilter();
        });
    }

    private void updateScrollViewItems(List<VehicleGson> data) {
        binding.bhSVItems.setAdapter(new BuyerVehicleListingsRecyclerViewAdapter(
                mainFacade.getMainActivity().getApplicationContext(),
                currentLocation,
                data, itemId -> {
            BuyerHome_FragmentDirections.ActionBuyerHomepageFragmentToSelectingCarFragment action =
                                    BuyerHome_FragmentDirections.actionBuyerHomepageFragmentToSelectingCarFragment();
                            action.setModelId(itemId);
                            mainFacade.getBuyerHomeNavController().navigate(action);
            }
        ));
    }

    private void toggleSpinnerFilter() {
        binding.bhSpinnerFilter.setVisibility(isSpinnerVisible ? View.INVISIBLE : View.VISIBLE);

        String[] filterOptions = {"Vehicle", "Dealership"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mainFacade.getMainActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, filterOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.bhSpinnerFilter.setAdapter(adapter);
        binding.bhSpinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedFilter = (String) parent.getItemAtPosition(position);
                ItemListingFilters filter = null;
                switch (selectedFilter) {
                    case "Vehicle":
                        filter = ItemListingFilters.VEHICLE;
                        break;
                    case "Dealership":
                        filter = ItemListingFilters.DEALERSHIP;
                        break;
                    default:
                        break;
                }
                if (filter != null) {
                    sortItemListings(filter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    private void updateItemListings(CharSequence s) {
        activeItemList.clear();
        for(VehicleGson items : itemList) {
            if(items.getModelAndName().toLowerCase().contains(s.toString().toLowerCase())) {
                activeItemList.add(items);
            }
        }
        updateScrollViewItems(activeItemList);
    }

    private void sortItemListings(ItemListingFilters filter) {
        switch (filter) {
            case VEHICLE:
                itemList.sort(Comparator.comparing(VehicleGson::getId));
                updateScrollViewItems(itemList);
                break;
            case DEALERSHIP:
                itemList.sort(Comparator.comparing(
                        vehicle -> vehicle.getDealershipGson().getId()
                ));
                updateScrollViewItems(itemList);
                break;
            default:
                itemList.sort(Comparator.comparing(VehicleGson::getModelAndName));
                updateScrollViewItems(itemList);
                break;
        }
    }
}