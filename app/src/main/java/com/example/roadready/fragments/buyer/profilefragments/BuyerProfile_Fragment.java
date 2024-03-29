package com.example.roadready.fragments.buyer.profilefragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.roadready.R;
import com.example.roadready.classes.general.SessionManager;
import com.example.roadready.databinding.FragmentBuyerProfileBinding;
import com.example.roadready.fragments.MainActivity;

public class BuyerProfile_Fragment extends Fragment {
    private static final String TAG = "BuyerProfile_Fragment"; // declare TAG for each class for debugging purposes using Log.d()
    private FragmentBuyerProfileBinding binding;
    private NavController navController;
    private SessionManager sessionManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View parent = getParentFragment().getView();
        navController = Navigation.findNavController(parent.findViewById(R.id.profileFragmentContainer));
//        navController = Navigation.findNavController(requireActivity(), R.id.profileFragmentContainer);

        sessionManager = new SessionManager(requireContext());

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

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    private void initActions() {
        binding.bpBtnLogout.setOnClickListener(v -> {
            sessionManager.stopSession();
            startActivity(new Intent(requireContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });

        binding.bpBtnEditProfile.setOnClickListener(v -> {
            navController.navigate(R.id.action_buyerProfile_Fragment_to_buyerEditProfile_Fragment);
        });
    }
}