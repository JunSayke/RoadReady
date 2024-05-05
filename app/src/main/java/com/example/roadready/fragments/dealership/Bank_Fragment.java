package com.example.roadready.fragments.dealership;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.FragmentDealershipBankBinding;

public class Bank_Fragment extends Fragment {
    private final String TAG = "Bank_Fragment"; // declare TAG for each class for debugging purposes using Log.d()
    private FragmentDealershipBankBinding binding; // use View binding to avoid using too much findViewById
    private MainFacade mainFacade;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDealershipBankBinding.inflate(inflater, container, false);
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

        binding.bankBtnSubmit.setOnClickListener(v -> {
            mainFacade.makeToast("Coming Soon", Toast.LENGTH_SHORT);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}