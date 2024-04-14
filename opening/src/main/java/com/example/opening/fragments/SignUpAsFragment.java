package com.example.opening.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.example.opening.OpeningFacade;
import com.example.opening.R;
import com.example.opening.databinding.FragmentSignUpAsBinding;

public class SignUpAsFragment extends Fragment {
    private static final String TAG = "SignUpAsFragment";
    private FragmentSignUpAsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignUpAsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        OpeningFacade openingFacade = OpeningFacade.getInstance();
        NavController openingNavController = openingFacade.getOpeningNavController();

        binding.spasTextLogin.setOnClickListener(v -> {
            openingNavController.navigate(R.id.action_signUpAsFragment_to_loginFragment);
        });

        binding.spasBtnVehicleBuyer.setOnClickListener(v -> {
            openingNavController.navigate(R.id.action_signUpAsFragment_to_buyerSignUpFragment);
        });

        binding.spasBtnSignupGoogle.setOnClickListener(v -> {
            openingFacade.makeToast("Login with google is not yet available!", Toast.LENGTH_SHORT);
        });

        binding.spasBtnDealer.setOnClickListener(v -> {

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}