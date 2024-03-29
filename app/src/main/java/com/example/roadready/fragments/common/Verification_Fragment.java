package com.example.roadready.fragments.common;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.roadready.R;
import com.example.roadready.databinding.FragmentVerificationBinding;

public class Verification_Fragment extends Fragment {

    private static final String TAG = "Verification_Activity"; // Declare TAG for each class for debugging purposes using Log.d()
    private FragmentVerificationBinding binding; // Use View binding to avoid using too much findViewById
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentVerificationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        navController = Navigation.findNavController(requireActivity(), R.id.openingFragmentContainer);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initActions();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initActions() {
        binding.vrfTextResendCode.setOnClickListener(v -> {

        });

        binding.vrfBtnSubmit.setOnClickListener(v -> {
            Log.d(TAG, String.valueOf(binding.vrfInptOtp.getText()));
        });
    }
}