package com.example.roadready.fragments.dealership.mainnavfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.roadready.databinding.FragmentDealershipForapprovalBinding;


public class ForApproval_Fragment extends Fragment {

		private final String TAG = "ForApproval_Fragment"; // declare TAG for each class for debugging purposes using Log.d()
		private FragmentDealershipForapprovalBinding binding; // use View binding to avoid using too much findViewById

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {

		binding = FragmentDealershipForapprovalBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		return root;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}
	
	