package com.example.roadready.fragments.dealership.mainnavfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.FragmentDealershipBanklistBinding;


public class Banklist_Fragment extends Fragment {

    private final String TAG = "Banklist_Fragment"; // declare TAG for each class for debugging purposes using Log.d()
    private FragmentDealershipBanklistBinding binding; // use View binding to avoid using too much findViewById
    private MainFacade mainFacade;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDealershipBanklistBinding.inflate(inflater, container, false);
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
        binding.blImageBdo.setOnClickListener(v -> {
            mainFacade.getDealershipBankNavController().navigate(R.id.action_banklist_Fragment_to_bank_Fragment);
        });
        binding.blImageUnionbank.setOnClickListener(v -> {
            mainFacade.getDealershipBankNavController().navigate(R.id.action_banklist_Fragment_to_bank_Fragment);
        });
    }
}
	
	