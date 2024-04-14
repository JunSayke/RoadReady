package com.example.opening.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.example.opening.OpeningFacade;
import com.example.opening.R;
import com.example.opening.databinding.FragmentOpeningBinding;

public class OpeningFragment extends Fragment {
    private static final String TAG = "OpeningFragment";
    private FragmentOpeningBinding binding;
    private static final long SPLASH_SCREEN_DURATION = 1000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOpeningBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OpeningFacade openingFacade = OpeningFacade.getInstance();
        NavController openingNavController = openingFacade.getOpeningNavController();
        new Handler().postDelayed(() -> {
            if (openingFacade.isLoggedIn()) {
                openingFacade.makeToast("Moving to Homepage", Toast.LENGTH_SHORT);

            } else {
                openingNavController.navigate(R.id.action_openingFragment_to_loginFragment);
            }
        }, SPLASH_SCREEN_DURATION);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}