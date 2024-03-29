package com.example.roadready.fragments.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roadready.R;
import com.example.roadready.classes.general.SessionManager;
import com.example.roadready.classes.model.gson.data.UserGson;
import com.example.roadready.databinding.FragmentOpeningBinding;

public class Opening_Fragment extends Fragment {
    private static final String TAG = "Opening_Fragment"; // declare TAG for each class for debugging purposes using Log.d()
    private FragmentOpeningBinding binding;
    private NavController navController;
    private static final long SPLASH_SCREEN_DURATION = 2000; // 3 seconds

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOpeningBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        navController = Navigation.findNavController(requireActivity(), R.id.openingFragmentContainer);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new Handler().postDelayed(() -> {
            UserGson user = new SessionManager(requireContext()).getUserGson();
            if (user != null) {
                navController.navigate(R.id.action_opening_Fragment_to_homePageContainer_Fragment);
            } else {
                navController.navigate(R.id.action_opening_Fragment_to_login_Fragment);
            }
        }, SPLASH_SCREEN_DURATION);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}