package com.example.roadready.fragments.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.FragmentOpeningBinding;

public class Opening_Fragment extends Fragment {
    private final String TAG = "Opening_Fragment";
    private FragmentOpeningBinding binding;
    private static final long SPLASH_SCREEN_DURATION = 1000;
    private MainFacade mainFacade;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOpeningBinding.inflate(inflater, container, false);
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

        new Handler().postDelayed(() -> {
            if (mainFacade.isLoggedIn()) {
                mainFacade.makeToast("Moving to Login page", Toast.LENGTH_SHORT);
                mainFacade.getMainNavGraphController().navigate(R.id.action_opening_Fragment_to_login_Fragment);
            } else {
                mainFacade.makeToast("Moving to Homepage", Toast.LENGTH_SHORT);
                mainFacade.getMainNavGraphController().navigate(R.id.action_opening_Fragment_to_homepageContainer_Fragment);
            }
        }, SPLASH_SCREEN_DURATION);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}