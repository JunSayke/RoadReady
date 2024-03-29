package com.example.roadready.fragments.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.roadready.R;
import com.example.roadready.databinding.FragmentSignUpAsBinding;

public class SignUpAs_Fragment extends Fragment {

    private static final String TAG = "SignUpAs_Fragment"; // Declare TAG for each class for debugging purposes using Log.d()
    private FragmentSignUpAsBinding binding; // Use View binding to avoid using too much findViewById
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignUpAsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        navController = Navigation.findNavController(requireActivity(), R.id.openingFragmentContainer);

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
            navController.navigate(R.id.action_signUpAs_Fragment2_to_login_Fragment);
        });

        binding.spasBtnVehicleBuyer.setOnClickListener(v -> {
            navController.navigate(R.id.action_signUpAs_Fragment2_to_signUp_Fragment);
        });

        binding.spasBtnDealer.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Dealer Registration is not yet available!", Toast.LENGTH_SHORT).show();
        });
    }
}