package com.example.roadready.fragments.buyer.home;

import android.os.Bundle;
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
import com.example.roadready.classes.ui.adapter.ListingsRecyclerViewAdapter;
import com.example.roadready.databinding.FragmentBuyerHomeBinding;

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

        mainFacade.getListings(
                null,
                null,
                null,
                null,
                new MainFacade.ResponseListener<ListingsDataGson>() {
                    @Override
                    public void onSuccess(ListingsDataGson data) {
                        binding.bhSVItems.setAdapter(new ListingsRecyclerViewAdapter(
                                mainFacade.getMainActivity().getApplicationContext(),
                                data.getListings(),
                                itemId -> {
                                    BuyerHome_FragmentDirections.ActionBuyerHomepageFragmentToSelectingCarFragment action =
                                            BuyerHome_FragmentDirections.actionBuyerHomepageFragmentToSelectingCarFragment();
                                    action.setModelId(itemId);
                                    mainFacade.getHomeNavGraphController().navigate(action);
                                }));
                        binding.bhSVItems.setLayoutManager(new LinearLayoutManager(mainFacade.getMainActivity().getApplicationContext()));
                    }

                    @Override
                    public void onFailure(String message) {
                        mainFacade.makeToast(message, Toast.LENGTH_SHORT);
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}