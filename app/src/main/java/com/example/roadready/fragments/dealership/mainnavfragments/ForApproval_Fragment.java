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

import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.ApplicationsDataGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.classes.ui.adapter.DealershipApplicationListingsRecyclerViewAdapter;
import com.example.roadready.databinding.FragmentDealershipForapprovalBinding;


public class ForApproval_Fragment extends Fragment {
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
		mainFacade.showProgressBar();
		final RoadReadyServer.ResponseListener<ApplicationsDataGson> responseListener = new RoadReadyServer.ResponseListener<ApplicationsDataGson>() {
			@Override
			public void onSuccess(SuccessGson<ApplicationsDataGson> response) {
				if (binding != null) {
					binding.faContainerApplicationList.setAdapter(new DealershipApplicationListingsRecyclerViewAdapter(
							mainFacade.getMainActivity().getApplicationContext(),
							response.getData().getApplications(),
							itemId -> {
								ForApproval_FragmentDirections.ActionForApprovalFragmentToVehicleApplicationProgressFragment action =
										ForApproval_FragmentDirections.actionForApprovalFragmentToVehicleApplicationProgressFragment();
								action.setModelId(itemId);
								mainFacade.getDealershipForApprovalNavController().navigate(action);
							}));
					binding.faContainerApplicationList.setLayoutManager(new LinearLayoutManager(mainFacade.getMainActivity().getApplicationContext()));
				}
				mainFacade.hideProgressBar();
			}

			@Override
			public void onFailure(int code, String message) {
				if (code != -1)
					mainFacade.makeToast(message, Toast.LENGTH_SHORT);
				mainFacade.hideProgressBar();
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
	
	