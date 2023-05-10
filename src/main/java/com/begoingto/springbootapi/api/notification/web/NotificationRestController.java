package com.begoingto.springbootapi.api.notification.web;

import com.begoingto.springbootapi.api.notification.NotificationService;
import com.begoingto.springbootapi.api.notification.NotificationServiceImpl;
import com.begoingto.springbootapi.base.BaseRest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationRestController {
    private final NotificationService notificationService;
    @PostMapping
    public BaseRest<?> pushNotification(@RequestBody NotificationDto notificationDto){

        boolean notification = notificationService.pushNotification(notificationDto);

        return null;
    }
}
