package com.example.roadready.fragments.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.FragmentSignUpAsBinding;

public class SignUpAs_Fragment extends Fragment {
    private final String TAG = "SignUpAs_Fragment";
    private FragmentSignUpAsBinding binding;
    private MainFacade mainFacade;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignUpAsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
        binding.spasTextLogin.setOnClickListener(v -> {
            mainFacade.getMainNavGraphController().navigate(R.id.action_global_login_Fragment);
        });

        binding.spasBtnVehicleBuyer.setOnClickListener(v -> {
            mainFacade.getMainNavGraphController().navigate(R.id.action_signUpAs_Fragment_to_signUp_Fragment);
        });

        binding.spasBtnSignupGoogle.setOnClickListener(v -> {
            mainFacade.makeToast("Login with google is not yet available!", Toast.LENGTH_SHORT);
        });

        binding.spasBtnDealer.setOnClickListener(v -> {
            mainFacade.makeToast("Dealer Registration is not yet available!", Toast.LENGTH_SHORT);
        });
    }
}