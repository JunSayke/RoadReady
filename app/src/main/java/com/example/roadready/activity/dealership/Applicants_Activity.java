package com.example.roadready.activity.dealership;

import android.app.Activity;
import android.os.Bundle;

import com.example.roadready.databinding.ActivityApplicantsBinding;
import com.example.roadready.databinding.ActivityApprovedBinding;

public class Applicants_Activity extends Activity {

	private final String TAG = "Applicants_Activity"; // declare TAG for each class for debugging purposes using Log.d()
	private ActivityApplicantsBinding binding; // use View binding to avoid using too much findViewById

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		binding = ActivityApplicantsBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

	}
}
	
	