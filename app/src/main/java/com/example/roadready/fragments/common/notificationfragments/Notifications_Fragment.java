package com.example.roadready.fragments.common.notificationfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.FragmentCommonNotificationsBinding;


public class Notifications_Fragment extends Fragment {
    private final String TAG = "Notifications_Fragment";
    private FragmentCommonNotificationsBinding binding;
    private MainFacade mainFacade;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = com.example.roadready.databinding.FragmentCommonNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
	
	