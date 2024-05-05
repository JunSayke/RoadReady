package com.example.roadready.fragments.buyer.home;

import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.DealershipsDataGson;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.data.DealershipGson;
import com.example.roadready.classes.model.gson.data.VehicleGson;
import com.example.roadready.classes.ui.adapter.BuyerDealershipListRecyclerViewAdapter;
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
    private List<VehicleGson> listingList;
    private List<VehicleGson> activeListingList;
    private List<DealershipGson> dealershipList;
    private List<DealershipGson> activeDealershipList;
    boolean isListingSelected = true; // true = vehicle, false = dealership
    private Location currentLocation;
    private final int HOT_VEHICLE_PRICE_THRESHOLD = 100000;
    private boolean isSpinnerVisible = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBuyerHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listingList = new ArrayList<>();
        activeListingList = new ArrayList<>();
        dealershipList = new ArrayList<>();
        activeDealershipList = new ArrayList<>();

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
        binding.getRoot().setVisibility(View.INVISIBLE);

        final RoadReadyServer.ResponseListener<DealershipsDataGson> dealershipResponseListener = new RoadReadyServer.ResponseListener<DealershipsDataGson>() {
            @Override
            public void onSuccess(DealershipsDataGson data) {
                for(DealershipGson dealershipGson : data.getDealerships()) {
                    dealershipList.add(dealershipGson);
                }
                updateDealershipScrollViewItems(dealershipList);
                binding.bhSVDealership.setLayoutManager(new LinearLayoutManager(mainFacade.getMainActivity().getApplicationContext()));
            }

            @Override
            public void onFailure(String message) {
                mainFacade.makeToast(message, Toast.LENGTH_SHORT);
            }
        };
        mainFacade.getDealerships(dealershipResponseListener, null, null);

        final RoadReadyServer.ResponseListener<ListingsDataGson> listingResponseListener = new RoadReadyServer.ResponseListener<ListingsDataGson>() {
            @Override
            public void onSuccess(ListingsDataGson data) {
                for(VehicleGson vehicleGson : data.getListings()) {
                    if(vehicleGson.isAvailable()) {
                        listingList.add(vehicleGson);
                    }
                }
                initHotListings();
                initSearchBar();
                updateListingScrollViewItems(listingList);
                binding.bhSVItems.setLayoutManager(new LinearLayoutManager(mainFacade.getMainActivity().getApplicationContext()));
                initFilterButton();
                mainFacade.hideProgressBar();
                binding.getRoot().setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(String message) {
                mainFacade.makeToast(message, Toast.LENGTH_SHORT);
                mainFacade.hideProgressBar();
                binding.getRoot().setVisibility(View.VISIBLE);
            }
        };

        mainFacade.getListings(listingResponseListener, null, null, null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initHotListings() {
        List<VehicleGson> filteredList = new ArrayList<>();
        for (VehicleGson item : listingList) {
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

    private void updateListingScrollViewItems(List<VehicleGson> data) {
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

    private void updateDealershipScrollViewItems(List<DealershipGson> data){
        binding.bhSVDealership.setAdapter(new BuyerDealershipListRecyclerViewAdapter(
                mainFacade.getMainActivity().getApplicationContext(),
                data, itemId -> {
            BuyerHome_FragmentDirections.ActionBuyerHomepageFragmentToSelectingDealershipFragment action =
                    BuyerHome_FragmentDirections.actionBuyerHomepageFragmentToSelectingDealershipFragment();
            action.setDealershipId(itemId);
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
                        binding.bhInptSearch.setHint(R.string.text_search_vehicle);
                        binding.bhSVDealership.setVisibility(View.GONE);
                        binding.bhSVItems.setVisibility(View.VISIBLE);
                        isListingSelected = true;
                        updateListingScrollViewItems(listingList);
                        filter = ItemListingFilters.VEHICLE;
                        break;
                    case "Dealership":
                        binding.bhInptSearch.setHint(R.string.text_search_dealership);
                        binding.bhSVDealership.setVisibility(View.VISIBLE);
                        binding.bhSVItems.setVisibility(View.GONE);
                        isListingSelected = false;
                        updateDealershipScrollViewItems(dealershipList);
                        filter = ItemListingFilters.DEALERSHIP;
                        break;
                    default:
                        break;
                }
//                if (filter != null) {
//                    sortItemListings(filter);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    private void updateItemListings(CharSequence s) {
        activeListingList.clear();
        activeDealershipList.clear();

        if(isListingSelected){
            for(VehicleGson items : listingList) {
                if(items.getModelAndName().toLowerCase().contains(s.toString().toLowerCase())) {
                    activeListingList.add(items);
                }
                if(activeListingList.isEmpty()){
                    binding.bhTxtSearchCount.setVisibility(View.VISIBLE);
                }else{
                    binding.bhTxtSearchCount.setVisibility(View.GONE);
                }
            }
            updateListingScrollViewItems(activeListingList);
        }else{
            for(DealershipGson items : dealershipList) {
                if(items.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                    activeDealershipList.add(items);
                }
                if(activeDealershipList.isEmpty()){
                    binding.bhTxtSearchCount.setVisibility(View.VISIBLE);
                }else{
                    binding.bhTxtSearchCount.setVisibility(View.GONE);
                }
            }
            updateDealershipScrollViewItems(activeDealershipList);
        }
    }

//    private void sortItemListings(ItemListingFilters filter) {
//        switch (filter) {
//            case VEHICLE:
//                listingList.sort(Comparator.comparing(VehicleGson::getId));
//                updateListingScrollViewItems(listingList);
//                break;
//            case DEALERSHIP:
//                listingList.sort(Comparator.comparing(
//                        vehicle -> vehicle.getDealershipGson().getId()
//                ));
//                updateListingScrollViewItems(listingList);
//                break;
//            default:
//                listingList.sort(Comparator.comparing(VehicleGson::getModelAndName));
//                updateListingScrollViewItems(listingList);
//                break;
//        }
//    }
}