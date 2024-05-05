package com.example.roadready.fragments.buyer.myvehiclefragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.UserDataGson;
import com.example.roadready.classes.model.gson.data.ApplicationGson;
import com.example.roadready.classes.model.gson.data.UserGson;
import com.example.roadready.databinding.FragmentBuyerMyVehicleBinding;

public class DocumentsSubmitted_Fragment extends Fragment {
    private final String TAG = "DocumentsSubmitted_Fragment";
    private com.example.roadready.databinding.FragmentBuyerDocumentsSubmittedBinding binding;
    private MainFacade mainFacade;

    private ApplicationGson applicationGson;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = com.example.roadready.databinding.FragmentBuyerDocumentsSubmittedBinding.inflate(inflater, container, false);
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

        final RoadReadyServer.ResponseListener<ApplicationGson> responseListener = new RoadReadyServer.ResponseListener<ApplicationGson>() {
            @Override
            public void onSuccess(ApplicationGson data) {
                String url = "No Application PDFs found!";
                if(data.getApplicationPdfUrl() != null) {
                    url = data.getApplicationPdfUrl();
                }
                TextView tv = new TextView(mainFacade.getMainActivity());
                tv.setText(url);
                binding.dsContainerDetail.addView(tv);

            }

            @Override
            public void onFailure(String message) {
                mainFacade.makeToast(message, Toast.LENGTH_SHORT);
            }
        };
        initActions();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initActions() {

    }
}