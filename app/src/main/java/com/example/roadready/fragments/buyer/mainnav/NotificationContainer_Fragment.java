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
import com.example.roadready.databinding.FragmentCommonNotificationContainerBinding;

public class NotificationContainer_Fragment extends Fragment {
    private final String TAG = "NotificationContainer_Fragment";
    private FragmentCommonNotificationContainerBinding binding;
    private MainFacade mainFacade;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCommonNotificationContainerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        mainFacade.hideProgressBar();
        mainFacade.hideBackDrop();

        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.common_notificationFragmentContainer);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        mainFacade.setCommonNotificationNavController(navController);
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