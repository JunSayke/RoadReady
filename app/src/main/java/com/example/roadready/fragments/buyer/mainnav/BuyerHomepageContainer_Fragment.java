package com.example.roadready.fragments.buyer.mainnav;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.response.ErrorGson;
import com.example.roadready.classes.model.gson.response.ResponseGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.classes.ui.adapter.ListingsRecyclerViewAdapter;
import com.example.roadready.databinding.FragmentBuyerHomepageBinding;
import com.google.gson.Gson;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BuyerHomepageContainer_Fragment extends Fragment {
    private static final String TAG = "HomeContainer_Fragment";
    private FragmentBuyerHomepageBinding binding;
    private final RoadReadyServer server = new RoadReadyServer();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBuyerHomepageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        server.getRetrofitService().getListings(null, null, null)
                .enqueue(getListingsCallback);
    }

    private final Callback<SuccessGson<ListingsDataGson>> getListingsCallback = new Callback<SuccessGson<ListingsDataGson>>() {
        @Override
        public void onResponse(@NonNull Call<SuccessGson<ListingsDataGson>> call, @NonNull Response<SuccessGson<ListingsDataGson>> response) {
            ResponseGson body = null;
            try {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    body = response.body();
                    Log.d(TAG, String.valueOf(body));
                    ListingsDataGson data = (ListingsDataGson) ((SuccessGson<?>) body).getData();
                    binding.bhSVItems.setAdapter(new ListingsRecyclerViewAdapter(requireContext(), data.getListings()));
                    binding.bhSVItems.setLayoutManager(new LinearLayoutManager(requireContext()));
                } else {
                    assert response.errorBody() != null;
                    body = new Gson().fromJson(response.errorBody().string(), ErrorGson.class);
                    Log.e(TAG, String.valueOf(body));
                }
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            } finally {
                if (body != null) {
                    Toast.makeText(requireContext(), String.valueOf(body.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(@NonNull Call<SuccessGson<ListingsDataGson>> call, @NonNull Throwable t) {
            Log.e(TAG, "Fetching Listings Failed!" + t.getMessage());
            Toast.makeText(requireContext(), "Network Error!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}