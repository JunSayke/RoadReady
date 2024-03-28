package com.example.roadready.activity.dealership;

import android.app.Activity;
import android.os.Bundle;

import com.example.roadready.databinding.ActivityMyVehiclesBinding;

public class MyVehicles_Activity extends Activity {
	private final String TAG = "MyVehicles_Activity"; // declare TAG for each class for debugging purposes using Log.d()
	private ActivityMyVehiclesBinding binding; // use View binding to avoid using too much findViewById

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		binding = ActivityMyVehiclesBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

	}
}
	
	