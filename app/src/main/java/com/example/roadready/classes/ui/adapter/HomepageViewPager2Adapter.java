package com.example.roadready.classes.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.roadready.fragments.buyer.mainnav.ApplicationContainer_Fragment;
import com.example.roadready.fragments.buyer.mainnav.BuyerHomepageContainer_Fragment;
import com.example.roadready.fragments.buyer.mainnav.MyVehicleContainer_Fragment;
import com.example.roadready.fragments.buyer.mainnav.NotificationContainer_Fragment;
import com.example.roadready.fragments.buyer.mainnav.ProfileContainer_Fragment;

public class HomepageViewPager2Adapter extends FragmentStateAdapter {
    private final Fragment[] fragments = {
            new BuyerHomepageContainer_Fragment(),
            new ApplicationContainer_Fragment(),
            new MyVehicleContainer_Fragment(),
            new NotificationContainer_Fragment(),
            new ProfileContainer_Fragment()
    };

    public HomepageViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments[position];
    }

    @Override
    public int getItemCount() {
        return fragments.length;
    }
}
