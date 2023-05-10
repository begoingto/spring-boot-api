package com.begoingto.springbootapi.api.notification;

import com.begoingto.springbootapi.api.notification.web.NotificationDto;

public interface NotificationService {
    boolean pushNotification(NotificationDto notificationDto);
}
