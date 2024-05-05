package com.example.roadready.fragments.buyer.myvehiclefragments;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.roadready.databinding.FragmentLayoutBuyerApplicationBinding;
import com.squareup.picasso.Picasso;

public class Application_Layout_Fragment extends Fragment {
    FragmentLayoutBuyerApplicationBinding binding;
    private final String documentUrl;

    public Application_Layout_Fragment(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLayoutBuyerApplicationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.apBtnDownloadDocuments.setOnClickListener(v -> {
            if (documentUrl != null) {
                DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(documentUrl));
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
            } else {
                Toast.makeText(getActivity(), "Your application document is not yet available!", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    public void setVehicleName(String vehicleName) {
        binding.apLblItem.setText(vehicleName);
    }

    public void setVehicleImage(String imageUrl) {
        Picasso.get().load(imageUrl).into(binding.apImageItem);
    }
}