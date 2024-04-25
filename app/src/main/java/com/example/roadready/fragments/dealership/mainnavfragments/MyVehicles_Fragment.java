package com.example.roadready.fragments.dealership.mainnavfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.data.UserGson;
import com.example.roadready.classes.ui.adapter.ListingsRecyclerViewAdapter;
import com.example.roadready.databinding.FragmentDealershipMyvehiclesBinding;
import com.example.roadready.fragments.buyer.home.BuyerHome_FragmentDirections;


public class MyVehicles_Fragment extends Fragment {
	private final String TAG = "MyVehicles_Fragment"; // declare TAG for each class for debugging purposes using Log.d()
	private FragmentDealershipMyvehiclesBinding binding; // use View binding to avoid using too much findViewById
	private MainFacade mainFacade;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {

		binding = FragmentDealershipMyvehiclesBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		try {
			mainFacade = MainFacade.getInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		UserGson userGson = mainFacade.getSessionManager().getUserGson();
		if(!userGson.isApproved()) {
			mainFacade.restrictImageButton(binding.mvBtnAddVehicle);
			mainFacade.restrictImageButton(binding.mvBtnRemoveVehicle);
		}

		return root;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		final RoadReadyServer.ResponseListener<ListingsDataGson> responseListener = new RoadReadyServer.ResponseListener<ListingsDataGson>() {
			@Override
			public void onSuccess(ListingsDataGson data) {
				binding.mvContainerVehicleList.setAdapter(new ListingsRecyclerViewAdapter(
						mainFacade.getMainActivity().getApplicationContext(),
						data.getListings(),
						itemId -> {
							BuyerHome_FragmentDirections.ActionBuyerHomepageFragmentToSelectingCarFragment action =
									BuyerHome_FragmentDirections.actionBuyerHomepageFragmentToSelectingCarFragment();
							action.setModelId(itemId);
							mainFacade.getDealershipHomepageNavController().navigate(action);
						}));
				binding.mvContainerVehicleList.setLayoutManager(new LinearLayoutManager(mainFacade.getMainActivity().getApplicationContext()));
			}

			@Override
			public void onFailure(String message) {
				mainFacade.makeToast(message, Toast.LENGTH_SHORT);
			}
		};

		initActions();
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}

	private void initActions() {
		binding.mvBtnRemoveVehicle.setOnClickListener(v -> {
			mainFacade.makeToast("Trashcan visibility toggle", Toast.LENGTH_SHORT);
		});
		binding.mvBtnAddVehicle.setOnClickListener(v -> {
			mainFacade.getDealershipMyVehicleNavController().navigate(R.id.action_myVehicles_Fragment_to_vehicleAdd_Fragment);
		});
	}
}
	
	