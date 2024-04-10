package com.example.roadready.fragments.dealership;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roadready.databinding.FragmentDealershipBankBinding;

public class Bank_Fragment extends Fragment {
    private final String TAG = "Bank_Fragment"; // declare TAG for each class for debugging purposes using Log.d()
    private FragmentDealershipBankBinding binding; // use View binding to avoid using too much findViewById

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDealershipBankBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}