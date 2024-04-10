package com.example.roadready.fragments.dealership;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.FragmentDealershipMyvehiclesBinding;


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

		return root;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

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
	
	