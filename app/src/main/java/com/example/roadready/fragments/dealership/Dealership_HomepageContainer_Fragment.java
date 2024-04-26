package com.example.roadready.fragments.dealership;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.FragmentDealershipHomepageContainerBinding;
import com.google.android.material.navigation.NavigationView;

public class Dealership_HomepageContainer_Fragment extends Fragment {
    private final String TAG = "Dealership_HomepageContainer_Fragment";
    private FragmentDealershipHomepageContainerBinding binding;
    private MainFacade mainFacade;
    private DrawerLayout dealershipDrawer;
    private NavigationView navigationViewDrawer;
    private View viewDrawer;
    private TextView txtName;
    private TextView txtTitle;
    private TextView headerText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDealershipHomepageContainerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dealershipDrawer = binding.dealershipHomepageContainerFragment;
        navigationViewDrawer = binding.dealershipNavigationView;
        viewDrawer = navigationViewDrawer.getHeaderView(0);
        txtName = viewDrawer.findViewById(R.id.sbTxtName);
        txtTitle = viewDrawer.findViewById(R.id.sbTxtDealershipName);
        headerText = binding.headerDealershipLayout.dealershipTxtHeader;

        try {
            mainFacade = MainFacade.getInstance();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.dealershipHomepageFragmentContainer);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        mainFacade.setDealershipHomepageNavController(navController);
        mainFacade.setCurrentNavController(navController);

        NavigationUI.setupWithNavController(navigationViewDrawer, navController);

        mainFacade.getUserGsonViewModel().getUserGsonLiveData().observe(getViewLifecycleOwner(), userGson -> {
            String name = userGson.getFirstName() + " " + userGson.getLastName();
            txtName.setText(name);
            txtTitle.setText(userGson.getDealership().getName());
        });

        mainFacade.getDealershipHomepageNavController().addOnDestinationChangedListener((controller, destination, arguments) -> {
            View mainHeader = binding.headerContainer;
            View profileHeader = binding.headerLayout.formHeader;

            switch(destination.getId() ){
                case R.id.navMyVehicles:
                    headerText.setText(R.string.text_header_my_vehicles );
                    break;
                case R.id.navForApproval:
                    headerText.setText(R.string.text_header_for_approval);
                    break;
                case R.id.navApproved:
                    headerText.setText(R.string.text_header_approved);
                    break;
                case R.id.navRegistrationProgress:
                    headerText.setText(R.string.text_header_documents_progress);
                    break;
                case R.id.navLTO:
                    headerText.setText(R.string.text_header_lto);
                    break;
                case R.id.navBank:
                    headerText.setText(R.string.text_header_banklist);
                    break;
                case R.id.navNotifications:
                    headerText.setText(R.string.text_header_notifications);
                    break;
            }
            if (destination.getId() == R.id.navProfile
                    && profileHeader.getVisibility() == View.GONE) {
                profileHeader.setVisibility(View.VISIBLE);
                mainHeader.setVisibility(View.GONE);
            } else if (destination.getId() != R.id.navProfile
                    && profileHeader.getVisibility() == View.VISIBLE) {
                profileHeader.setVisibility(View.GONE);
                mainHeader.setVisibility(View.VISIBLE);
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
        mainFacade.getMainActivity().findViewById(R.id.btnOpenSidebar).setOnClickListener(v -> {
            dealershipDrawer.openDrawer(Gravity.LEFT);
        });
    }
}
