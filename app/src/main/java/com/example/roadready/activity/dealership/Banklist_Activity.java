package com.example.roadready.activity.dealership;

import android.app.Activity;
import android.os.Bundle;

import com.example.roadready.databinding.ActivityBanklistBinding;

	public class Banklist_Activity extends Activity {

		private final String TAG = "Banklist_Activity"; // declare TAG for each class for debugging purposes using Log.d()
		private ActivityBanklistBinding binding; // use View binding to avoid using too much findViewById

		@Override
		public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			binding = ActivityBanklistBinding.inflate(getLayoutInflater());
			setContentView(binding.getRoot());

		}
}
	
	