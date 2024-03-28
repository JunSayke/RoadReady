
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		registration_docs
	 *	@date 		Thursday 28th of March 2024 12:45:37 AM
	 *	@title 		Page 1
	 *	@author 	
	 *	@keywords 	
	 *	@generator 	Export Kit v1.3.figma
	 *
	 */
	

package com.example.roadready.activity.dealership;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.roadready.databinding.ActivityRegistrationDocsBinding;

	public class RegistrationDocs_Activity extends Activity {
		private final String TAG = "RegistrationDocs_Activity"; // declare TAG for each class for debugging purposes using Log.d()
		private ActivityRegistrationDocsBinding binding; // use View binding to avoid using too much findViewById

		@Override
		public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			binding = ActivityRegistrationDocsBinding.inflate(getLayoutInflater());
			setContentView(binding.getRoot());

			//There is a much better way of implementing a dropdown list, but this is good for now
			String[] items = new String[]{"Office Location", "Mandaue", "Robinsons Galleria"};
			ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
			binding.rdSpinnerSubmitLto.setAdapter(adapter);
		}
}
	
	