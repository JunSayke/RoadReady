package com.example.roadready.fragments.common;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.model.gson.data.UserGson;
import com.example.roadready.databinding.FragmentCommonOpeningBinding;

public class Opening_Fragment extends Fragment {
    private final String TAG = "Opening_Fragment";
    private FragmentCommonOpeningBinding binding;
    private static final long SPLASH_SCREEN_DURATION = 1000;
    private MainFacade mainFacade;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCommonOpeningBinding.inflate(inflater, container, false);
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
                UserGson userGson = mainFacade.getSessionManager().getUserGson();
                if (userGson.getRole().equals("buyer")) {
                    mainFacade.getCommonMainNavController().navigate(R.id.action_opening_Fragment_to_homepageContainer_Fragment);
                } else {
                    mainFacade.getCommonMainNavController().navigate(R.id.action_opening_Fragment_to_dealership_homepageContainer_Fragment);
                }
            } else {
                mainFacade.getCommonMainNavController().navigate(R.id.action_opening_Fragment_to_login_Fragment);
            }
        }, SPLASH_SCREEN_DURATION);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}