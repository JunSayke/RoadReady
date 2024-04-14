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
import com.example.opening.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OpeningFacade openingFacade = OpeningFacade.getInstance();

        binding.lgnBtnLogin.setOnClickListener(v -> {
            OpeningFacade.toHomepage.navigate();
        });

        binding.lgnTextSignup.setOnClickListener(v -> {
            openingFacade.getOpeningNavController().navigate(R.id.action_loginFragment_to_signUpAsFragment);
        });

        binding.lgnBtnGoogleLogin.setOnClickListener(v -> {
            openingFacade.makeToast("Login with google is not yet available!", Toast.LENGTH_SHORT);
        });

        binding.lgnTextForgetPassword.setOnClickListener(v -> {
            openingFacade.makeToast("Forgot password is not yet available!", Toast.LENGTH_SHORT);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}