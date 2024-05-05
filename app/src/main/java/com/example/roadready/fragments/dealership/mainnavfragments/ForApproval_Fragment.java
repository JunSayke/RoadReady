package com.example.roadready.fragments.dealership.mainnavfragments;

import android.os.Bundle;
import android.util.Log;
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
import com.example.roadready.classes.model.gson.ApplicationsDataGson;
import com.example.roadready.classes.ui.adapter.DealershipApplicationListingsRecyclerViewAdapter;
import com.example.roadready.databinding.FragmentDealershipForapprovalBinding;


public class ForApproval_Fragment extends Fragment {

	private final String TAG = "ForApproval_Fragment"; // declare TAG for each class for debugging purposes using Log.d()
	private FragmentDealershipForapprovalBinding binding; // use View binding to avoid using too much findViewById

	private MainFacade mainFacade;
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {

		binding = FragmentDealershipForapprovalBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		try {
			mainFacade = MainFacade.getInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return root;
	}

	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		final RoadReadyServer.ResponseListener<ApplicationsDataGson> responseListener = new RoadReadyServer.ResponseListener<ApplicationsDataGson>() {
			@Override
			public void onSuccess(ApplicationsDataGson data) {
				Log.d(TAG, String.valueOf(data));
				binding.faContainerApplicationList.setAdapter(new DealershipApplicationListingsRecyclerViewAdapter(
						mainFacade.getMainActivity().getApplicationContext(),
						data.getApplications(),
						itemId -> {
							ForApproval_FragmentDirections.ActionForApprovalFragmentToVehicleApplicationProgressFragment action =
									ForApproval_FragmentDirections.actionForApprovalFragmentToVehicleApplicationProgressFragment();
							action.setModelId(itemId);
							mainFacade.getDealershipForApprovalNavController().navigate(action);
						}));
				binding.faContainerApplicationList.setLayoutManager(new LinearLayoutManager(mainFacade.getMainActivity().getApplicationContext()));
			}

			@Override
			public void onFailure(String message) {
				mainFacade.makeToast(message, Toast.LENGTH_SHORT);
			}
		};
		mainFacade.getDealershipApplications(responseListener);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}
	
	