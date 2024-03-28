package com.example.roadready.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roadready.R;
import com.example.roadready.classes.general.SessionManager;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.data.VehicleGson;
import com.example.roadready.classes.model.gson.response.ErrorGson;
import com.example.roadready.classes.model.gson.response.ResponseGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.classes.model.livedata.UserGsonViewModel;
import com.example.roadready.classes.retrofit.RetrofitFacade;
import com.example.roadready.classes.ui.adapter.MyVehicleRecyclerViewAdapter;
import com.example.roadready.databinding.ActivityBuyerHomepageBinding;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyerHomepage_Activity extends AppCompatActivity {
    private final String TAG = "BuyerHomepage_Activity"; // Declare TAG for each class for debugging purposes using Log.d()
    private ActivityBuyerHomepageBinding binding; // Use View binding to avoid using too much findViewById
    private final RetrofitFacade retrofitFacade = new RetrofitFacade("https://road-ready-black.vercel.app"); // Declare this if HTTP operations are needed
    private  UserGsonViewModel userGsonViewModel; // Use Live Data for efficiently managing real time data changes
    private SessionManager sessionManager; // Holder for user data's and login session

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyerHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userGsonViewModel = new ViewModelProvider(this).get(UserGsonViewModel.class);
        sessionManager = new SessionManager(getApplicationContext());
        observeLiveData();

        userGsonViewModel.setUserGsonLiveData(sessionManager.getUserGson());

        retrofitFacade.getRetrofitService().getListings(null, null, null)
                .enqueue(getListingsCallback);

        Log.d(TAG, sessionManager.getUserGson().toString());
        sessionManager.stopSession();
    }

    private final Callback< SuccessGson<ListingsDataGson> > getListingsCallback = new Callback< SuccessGson<ListingsDataGson> >() {
        @Override
        public void onResponse(@NonNull Call< SuccessGson<ListingsDataGson> > call, @NonNull Response< SuccessGson<ListingsDataGson> > response) {
            ResponseGson body = null;
            try {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    body = response.body();
                    Log.d(TAG, String.valueOf(body));
                    ListingsDataGson data = (ListingsDataGson) ((SuccessGson<?>) body).getData();
                    List<VehicleGson> listings = data.getListings();
                    assert listings != null;
                    MyVehicleRecyclerViewAdapter dealershipAdapter = new MyVehicleRecyclerViewAdapter(getApplicationContext(), listings);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    binding.bhSVItems.setLayoutManager(linearLayoutManager);
                    binding.bhSVItems.setAdapter(dealershipAdapter);
                } else {
                    assert response.errorBody() != null;
                    body = new Gson().fromJson(response.errorBody().string(), ErrorGson.class);
                }
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            } finally {
                if (body != null) {
                    Toast.makeText(getApplicationContext(), String.valueOf(body.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(@NonNull Call< SuccessGson<ListingsDataGson> > call, Throwable t) {
            Log.e(TAG, "Get Listings Failed! " + t.getMessage());
        }
    };

    private void observeLiveData() {
        userGsonViewModel.getUserGsonLiveData().observe(this, userGson -> {
            updateWelcomeText(userGson.getFirstName());
        });
    }
    private void updateWelcomeText(String displayName) {
        String welcomeUser = "Welcome " + displayName;
        binding.bhTextWelcomeUser.setText(welcomeUser);
    }
}