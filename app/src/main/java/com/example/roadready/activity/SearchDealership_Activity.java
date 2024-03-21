package com.example.roadready.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.roadready.R;
import com.example.roadready.databinding.ActivitySearchDealershipBinding;

public class SearchDealership_Activity extends AppCompatActivity {

    private final String TAG = "SearchDealership_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private ActivitySearchDealershipBinding binding; // use View binding to avoid using too much findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dealership);
    }
}