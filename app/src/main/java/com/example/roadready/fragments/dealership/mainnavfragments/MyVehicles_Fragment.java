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
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.classes.ui.adapter.DealershipVehicleListingsRecyclerViewAdapter;
import com.example.roadready.databinding.FragmentDealershipMyvehiclesBinding;

public class MyVehicles_Fragment extends Fragment {
	private final String TAG = "MyVehicles_Fragment"; // declare TAG for each class for debugging purposes using Log.d()
	private FragmentDealershipMyvehiclesBinding binding; // use View binding to avoid using too much findViewById
	private MainFacade mainFacade;
	private int listingCount;
	private UserGson userGson;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {

		binding = FragmentDealershipMyvehiclesBinding.inflate(inflater, container, false);
		View root = binding.getRoot();
		listingCount = 0;

		try {
			mainFacade = MainFacade.getInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		userGson = mainFacade.getSessionManager().getUserGson();
		if(!userGson.getIsApproved()) {
			mainFacade.restrictImageButton(binding.mvBtnAddVehicle);
		}
		binding.mvTxtTitle.setText(userGson.getDealership().getName());

		return root;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mainFacade.showProgressBar();
		final RoadReadyServer.ResponseListener<ListingsDataGson> responseListener = new RoadReadyServer.ResponseListener<ListingsDataGson>() {
			@Override
			public void onSuccess(SuccessGson<ListingsDataGson> response) {
				if (binding != null) {
					binding.mvContainerVehicleList.setAdapter(new DealershipVehicleListingsRecyclerViewAdapter(
							mainFacade.getMainActivity().getApplicationContext(),
							response.getData().getListings(),
							itemId -> {
								MyVehicles_FragmentDirections.ActionMyVehiclesFragmentToVehicleListingFragment action =
										MyVehicles_FragmentDirections.actionMyVehiclesFragmentToVehicleListingFragment();
								action.setModelId(itemId);
								mainFacade.getDealershipMyVehicleNavController().navigate(action);
							}));
					binding.mvContainerVehicleList.setLayoutManager(new LinearLayoutManager(mainFacade.getMainActivity().getApplicationContext()));
				}

				listingCount = response.getData().getListings().size();
				setListingCount();
				mainFacade.hideProgressBar();
			}

			@Override
			public void onFailure(int code, String message) {
				if (code != -1)
					mainFacade.makeToast(message, Toast.LENGTH_SHORT);
				setListingCount();
				mainFacade.hideProgressBar();
			}
		};

		mainFacade.getListings(responseListener, null, userGson.getDealership().getId(), null);

		initActions();
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}

	private void initActions() {
		binding.mvBtnAddVehicle.setOnClickListener(v -> {
			mainFacade.getDealershipMyVehicleNavController().navigate(R.id.action_myVehicles_Fragment_to_vehicleAdd_Fragment);
		});
	}

	private void setListingCount() {
		if(listingCount <= 0){
			binding.mvTxtListingCount.setVisibility(View.VISIBLE);
		}
	}
}
	
	