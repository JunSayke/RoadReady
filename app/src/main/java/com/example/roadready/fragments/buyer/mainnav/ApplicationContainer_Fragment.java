package com.example.roadready.fragments.buyer.mainnav;

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
import com.example.roadready.databinding.FragmentBuyerApplicationContainerBinding;

public class ApplicationContainer_Fragment extends Fragment {
    private final String TAG = "ApplicationContainer_Fragment";
    private FragmentBuyerApplicationContainerBinding binding;
    private MainFacade mainFacade = MainFacade.getInstance();

    public ApplicationContainer_Fragment() throws Exception {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerApplicationContainerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.buyer_applicationFragmentContainer);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        mainFacade.setBuyerApplicationNavController(navController);
        mainFacade.setCurrentNavController(navController);

        navController.popBackStack(navController.getGraph().getStartDestinationId(), false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}