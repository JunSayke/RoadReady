package com.example.roadready.fragments.common;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.roadready.R;
import com.example.roadready.classes.model.livedata.BuyerGsonViewModel;
import com.example.roadready.classes.model.livedata.BuyerGsonViewModelFactory;
import com.example.roadready.classes.ui.adapter.HomepageViewPager2Adapter;
import com.example.roadready.databinding.FragmentHomepageContainerBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomepageContainer_Fragment extends Fragment {
    private static final String TAG = "HomePageContainer_Fragment";
    private FragmentHomepageContainerBinding binding;
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private BuyerGsonViewModel userGsonViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomepageContainerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewPager2 = binding.homePageViewPager2;
        bottomNavigationView = binding.homePageBottomNav;
        viewPager2.setAdapter(new HomepageViewPager2Adapter(requireActivity()));

        BuyerGsonViewModelFactory factory = new BuyerGsonViewModelFactory(requireContext());
        userGsonViewModel = new ViewModelProvider(this, factory).get(BuyerGsonViewModel.class);

        userGsonViewModel.getBuyerGsonLiveData().observe(getViewLifecycleOwner(), buyerGson -> {
            TextView view = requireActivity().findViewById(R.id.bhTextWelcomeUser);

            String welcomeText = "Welcome " + buyerGson.getFirstName();
            view.setText(welcomeText);
        });

        return root;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mnHome:
                    viewPager2.setCurrentItem(0, false);
                    return true;
                case R.id.mnApplication:
                    viewPager2.setCurrentItem(1, false);
                    return true;
                case R.id.mnMyVehicle:
                    viewPager2.setCurrentItem(2, false);
                    return true;
                case R.id.mnNotification:
                    viewPager2.setCurrentItem(3, false);
                    return true;
                case R.id.mnProfile:
                    viewPager2.setCurrentItem(4, false);
                    return true;
                default:
                    return false;
            }
        });


        // Sync ViewPager2 and Bottom Navigation View
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomNavigationView.setSelectedItemId(getMenuItemId(position));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private int getMenuItemId(int position) {
        switch (position) {
            case 0:
                return R.id.mnHome;
            case 1:
                return R.id.mnApplication;
            case 2:
                return R.id.mnMyVehicle;
            case 3:
                return R.id.mnNotification;
            case 4:
                return R.id.mnProfile;
            default:
                return -1;
        }
    }
}