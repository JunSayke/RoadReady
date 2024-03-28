package com.example.roadready.activity.dealership;

import android.app.Activity;
import android.os.Bundle;

import com.example.roadready.databinding.ActivityVehicleAddBinding;

	public class VehicleAdd_Activity extends Activity {
		private final String TAG = "VehicleAdd_Activity"; // declare TAG for each class for debugging purposes using Log.d()
		private ActivityVehicleAddBinding binding; // use View binding to avoid using too much findViewById

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		binding = ActivityVehicleAddBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

	}
}
	
	