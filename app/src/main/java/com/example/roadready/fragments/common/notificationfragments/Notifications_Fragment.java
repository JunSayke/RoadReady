package com.example.roadready.fragments.common.notificationfragments;

import android.os.Bundle;
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
import com.example.roadready.classes.model.gson.response.SuccessGson;
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
        notificationCount = 0;

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        mainFacade.hideProgressBar();
        mainFacade.hideBackDrop();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainFacade.showProgressBar();
        final RoadReadyServer.ResponseListener<NotificationsDataGson> responseListener = new RoadReadyServer.ResponseListener<NotificationsDataGson>() {
            @Override
            public void onSuccess(SuccessGson<NotificationsDataGson> response) {
                if (binding != null) {
                    binding.nContainerNotifications.setAdapter(new CommonNotificationListingsRecyclerViewAdapter(
                            mainFacade.getMainActivity().getApplicationContext(),
                            response.getData().getNotifications(),
                            itemId -> deleteNotification(itemId)));
                    binding.nContainerNotifications.setLayoutManager(new LinearLayoutManager(mainFacade.getMainActivity().getApplicationContext()));
                }

                notificationCount = response.getData().getNotifications().size();
                setNotificationCount();
                mainFacade.hideProgressBar();
            }

            @Override
            public void onFailure(int code, String message) {
                if (code != -1)
                    mainFacade.makeToast(message, Toast.LENGTH_SHORT);
                setNotificationCount();
                mainFacade.hideProgressBar();
            }
        };

        mainFacade.getNotification(responseListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        mainFacade.hideProgressBar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    private void deleteNotification(String itemId){
        mainFacade.showProgressBar();
        mainFacade.showBackDrop();
        final RoadReadyServer.ResponseListener<GsonData> responseListener = new RoadReadyServer.ResponseListener<GsonData>() {
            @Override
            public void onSuccess(SuccessGson<GsonData> response) {
                mainFacade.makeToast(response.getMessage(), Toast.LENGTH_SHORT);
                mainFacade.hideProgressBar();
                mainFacade.hideBackDrop();
            }

            @Override
            public void onFailure(int code, String message) {
                if (code != -1)
                    mainFacade.makeToast(message, Toast.LENGTH_SHORT);
                mainFacade.hideProgressBar();
                mainFacade.hideBackDrop();
            }
        };

        mainFacade.deleteNotification(responseListener, itemId);
    }

    public void setNotificationCount(){
        if (binding != null) {
            if(notificationCount <= 0){
                binding.nTxtNotificationCount.setVisibility(View.VISIBLE);
            }
        }
    }
}
	
	