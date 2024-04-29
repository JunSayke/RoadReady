package com.example.roadready.classes.model.gson;

import com.example.roadready.classes.model.gson.data.NotificationGson;

import java.util.List;

public class NotificationsDataGson extends GsonData {
    private List<NotificationGson> notifications;

    public List<NotificationGson> getNotifications() {
        return notifications;
    }
}
