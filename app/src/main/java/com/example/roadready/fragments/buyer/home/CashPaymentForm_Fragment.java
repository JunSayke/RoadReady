package com.example.roadready.fragments.buyer.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.FragmentBuyerCashPaymentFormBinding;

public class CashPaymentForm_Fragment extends Fragment {
    private final String TAG = "CashPaymentForm_Fragment";
    private FragmentBuyerCashPaymentFormBinding binding;
    private MainFacade mainFacade;
    private String modelId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerCashPaymentFormBinding.inflate(inflater, container, false);
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
        modelId = CashPaymentForm_FragmentArgs.fromBundle(getArguments()).getModelId();
        mainFacade.makeToast(modelId, Toast.LENGTH_SHORT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initActions() {
        binding.cpfBtnSubmit.setOnClickListener(v -> {
            // TODO: Handle Submit Action Event
            mainFacade.makeToast("Currently under construction!", Toast.LENGTH_SHORT);
        });

        binding.cpfBtnCancel.setOnClickListener(v -> {
            // TODO: Handle Cancel Action Event
            mainFacade.makeToast("Currently under construction!", Toast.LENGTH_SHORT);
        });
    }
}