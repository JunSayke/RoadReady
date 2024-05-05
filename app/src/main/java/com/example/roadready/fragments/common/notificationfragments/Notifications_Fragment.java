package com.example.roadready.fragments.common.notificationfragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.classes.general.RoadReadyServer;
import com.example.roadready.classes.model.gson.GsonData;
import com.example.roadready.classes.model.gson.NotificationsDataGson;
import com.example.roadready.classes.ui.adapter.CommonNotificationListingsRecyclerViewAdapter;
import com.example.roadready.databinding.FragmentCommonNotificationsBinding;

public class Notifications_Fragment extends Fragment {
    private final String TAG = "Notifications_Fragment";
    private FragmentCommonNotificationsBinding binding;
    private MainFacade mainFacade;
    private int notificationCount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = com.example.roadready.databinding.FragmentCommonNotificationsBinding.inflate(inflater, container, false);
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
        final RoadReadyServer.ResponseListener<NotificationsDataGson> responseListener = new RoadReadyServer.ResponseListener<NotificationsDataGson>() {
            @Override
            public void onSuccess(NotificationsDataGson data) {

                binding.nContainerNotifications.setAdapter(new CommonNotificationListingsRecyclerViewAdapter(
                        mainFacade.getMainActivity().getApplicationContext(),
                        data.getNotifications(),
                        itemId -> deleteNotification(itemId)));
                binding.nContainerNotifications.setLayoutManager(new LinearLayoutManager(mainFacade.getMainActivity().getApplicationContext()));

                notificationCount = data.getNotifications().size();
                setListingCount();
            }

            @Override
            public void onFailure(String message) {
                mainFacade.makeToast(message, Toast.LENGTH_SHORT);
            }
        };

        mainFacade.getNotification(responseListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void deleteNotification(String itemId){
        final RoadReadyServer.ResponseListener<GsonData> responseListener = new RoadReadyServer.ResponseListener<GsonData>() {
            @Override
            public void onSuccess(GsonData data) {

            }

            @Override
            public void onFailure(String message) {
                mainFacade.makeToast(message, Toast.LENGTH_SHORT);
            }
        };

        mainFacade.deleteNotification(responseListener, itemId);
    }

    private void setListingCount() {
        if(notificationCount <= 0){
            binding.nTxtNotificationCount.setVisibility(View.VISIBLE);
        }
    }
}
	
	