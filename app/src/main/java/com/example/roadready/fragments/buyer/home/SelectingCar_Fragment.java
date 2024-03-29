package com.example.roadready.fragments.buyer.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roadready.databinding.FragmentSelectingCarBinding;

public class SelectingCar_Fragment extends Fragment {
    private static final String TAG = "SelectingCar_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private FragmentSelectingCarBinding binding; // use View binding to avoid using too much findViewById

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectingCarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}