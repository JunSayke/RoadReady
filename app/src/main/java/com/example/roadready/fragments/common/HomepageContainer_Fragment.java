package com.example.roadready.fragments.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.model.gson.data.UserGson;
import com.example.roadready.databinding.FragmentBuyerHomepageContainerBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomepageContainer_Fragment extends Fragment {
    private final String TAG = "HomepageContainer_Fragment";
    private FragmentBuyerHomepageContainerBinding binding;
    private BottomNavigationView bottomNavigationView;
    private MainFacade mainFacade;
    private boolean isApproved = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBuyerHomepageContainerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        bottomNavigationView = binding.homePageBottomNav;

        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.homepageFragmentContainer);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        mainFacade.setBuyerHomepageNavController(navController);
        mainFacade.setCurrentNavController(navController);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        mainFacade.getUserGsonViewModel().getUserGsonLiveData().observe(getViewLifecycleOwner(), userGson -> {
            TextView textWelcomeUser = mainFacade.getMainActivity().findViewById(R.id.bhTextWelcomeUser);

            String welcomeText = "Welcome " + userGson.getFirstName();
            textWelcomeUser.setText(welcomeText);

            if(!userGson.isApproved()) {
                mainFacade.getMainActivity().findViewById(R.id.bhTextVerifcation).setVisibility(View.VISIBLE);
                isApproved = false;
            }
        });

        // Toggle header
        mainFacade.getBuyerHomepageNavController().addOnDestinationChangedListener((controller, destination, arguments) -> {
            View welcomeHeader = binding.welcomeHeaderLayout.formHeader;
            View regularHeader = binding.headerLayout.formHeader;
            TextView welcomeVerifyMsg = binding.welcomeHeaderLayout.bhTextVerifcation;
            TextView regularVerifyMsg = binding.headerLayout.bepTextVerifcation;

            if (destination.getId() == R.id.mnHome
                    && welcomeHeader.getVisibility() == View.GONE) {
                welcomeHeader.setVisibility(View.VISIBLE);
                regularHeader.setVisibility(View.GONE);
                if(!isApproved){
                    welcomeVerifyMsg.setVisibility(View.VISIBLE);
                }
            } else if (destination.getId() != R.id.mnHome
                    && welcomeHeader.getVisibility() == View.VISIBLE) {
                welcomeHeader.setVisibility(View.GONE);
                regularHeader.setVisibility(View.VISIBLE);
                if(!isApproved){
                    regularVerifyMsg.setVisibility(View.VISIBLE);
                }
            }
        });

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
        binding.headerLayout.bepBtnBack.setOnClickListener(v -> {
            mainFacade.getMainActivity().getOnBackPressedDispatcher().onBackPressed();
        });
    }
}