package com.example.roadready.fragments.dealership.mainnav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.FragmentDealershipBankContainerBinding;

public class Dealership_Bank_Container_Fragment extends Fragment {
    private final String TAG = "Dealership_Bank_Container_Container_Fragment";
    private FragmentDealershipBankContainerBinding binding;
    private MainFacade mainFacade;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDealershipBankContainerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        mainFacade.hideProgressBar();
        mainFacade.hideBackDrop();

        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.dealership_bankFragmentContainer);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        mainFacade.setDealershipBankNavController(navController);
        mainFacade.setCurrentNavController(navController);

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

    }
}