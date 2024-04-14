package com.example.opening.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.opening.OpeningFacade;
import com.example.opening.R;
import com.example.opening.databinding.FragmentBuyerSignUpBinding;

public class BuyerSignUpFragment extends Fragment {
    private static final String TAG = "SignUpFragment";
    private FragmentBuyerSignUpBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerSignUpBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.sgnupBtnSubmit.setOnClickListener(v -> {

        });

        OpeningFacade openingFacade = OpeningFacade.getInstance();
        binding.sgnupTextLogin.setOnClickListener(v -> {
            openingFacade.getOpeningNavController().navigate(R.id.action_buyerSignUpFragment_to_loginFragment);
        });

        binding.sgnupBtnOpenMaps.setOnClickListener(v -> {

        });

        binding.sgnupBtnGoogleLogin.setOnClickListener(v -> {
            openingFacade.makeToast("Login with google is not yet available!", Toast.LENGTH_SHORT);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}