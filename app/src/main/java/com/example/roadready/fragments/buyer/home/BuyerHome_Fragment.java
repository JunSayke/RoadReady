package com.example.roadready.fragments.buyer.home;

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

import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.model.gson.ListingsDataGson;
import com.example.roadready.classes.model.gson.response.ErrorGson;
import com.example.roadready.classes.model.gson.response.ResponseGson;
import com.example.roadready.classes.model.gson.response.SuccessGson;
import com.example.roadready.classes.ui.adapter.ListingsRecyclerViewAdapter;
import com.example.roadready.databinding.FragmentBuyerHomeBinding;
import com.google.gson.Gson;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyerHome_Fragment extends Fragment {
    private final String TAG = "BuyerHome_Fragment";
    private FragmentBuyerHomeBinding binding;
    private MainFacade mainFacade;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBuyerHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainFacade.getServer().getRetrofitService().getListings(null, null, null)
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
                    binding.bhSVItems.setAdapter(new ListingsRecyclerViewAdapter(requireContext(), data.getListings(),
                            itemId -> {
                                BuyerHome_FragmentDirections.ActionBuyerHomepageFragmentToSelectingCarFragment action =
                                        BuyerHome_FragmentDirections.actionBuyerHomepageFragmentToSelectingCarFragment();
                                action.setModelId(itemId);
                                mainFacade.getHomeNavGraphController().navigate(action);
                            }));
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
                    mainFacade.makeToast(body.getMessage(), Toast.LENGTH_SHORT);
                }
            }
        }

        @Override
        public void onFailure(@NonNull Call<SuccessGson<ListingsDataGson>> call, @NonNull Throwable t) {
            Log.e(TAG, "Fetching Listings Failed!" + t.getMessage());
            mainFacade.makeToast("Network Error!", Toast.LENGTH_SHORT);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}