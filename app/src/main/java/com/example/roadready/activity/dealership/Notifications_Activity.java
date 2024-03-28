
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		notifications
	 *	@date 		Thursday 28th of March 2024 05:39:52 AM
	 *	@title 		Page 1
	 *	@author 	
	 *	@keywords 	
	 *	@generator 	Export Kit v1.3.figma
	 *
	 */
	

package com.example.roadready.activity.dealership;

import android.app.Activity;
import android.os.Bundle;


import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.roadready.databinding.ActivityApplicantsBinding;
import com.example.roadready.databinding.ActivityNotificationsBinding;

	public class Notifications_Activity extends Activity {

		private final String TAG = "Notifications_Activity"; // declare TAG for each class for debugging purposes using Log.d()
		private ActivityNotificationsBinding binding; // use View binding to avoid using too much findViewById

		@Override
		public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
			setContentView(binding.getRoot());

			//swipe to delete concept, if dili kaya, lmk cause mag add rako og trash bin icons :))
			//https://www.youtube.com/watch?v=IlI6GgC_j78

		}
}
	
	