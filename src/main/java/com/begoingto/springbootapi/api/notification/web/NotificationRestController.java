package com.begoingto.springbootapi.api.notification.web;

import com.begoingto.springbootapi.api.notification.NotificationService;
import com.begoingto.springbootapi.api.notification.NotificationServiceImpl;
import com.begoingto.springbootapi.base.BaseRest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationRestController {
    private final NotificationService notificationService;
    @PostMapping
    public BaseRest<?> pushNotification(@RequestBody NotificationDto notificationDto){
        boolean notification = notificationService.pushNotification(notificationDto);
        if (!notification){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Notification cannot push");
        }
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Notification has been pushed.")
                .timestamp(LocalDateTime.now())
                .build();
    }
}
