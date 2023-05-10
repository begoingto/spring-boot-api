package com.begoingto.springbootapi.api.notification;

import com.begoingto.springbootapi.api.notification.web.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService{
    private RestTemplate restTemplate;

    @Value("${onesignal.api_key}")
    private String apiKey;

    @Value("${onesignal.app_id}")
    private String appId;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean pushNotification(NotificationDto notificationDto) {

        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.set("accept", "application/json");
        httpHeaders.set("Authorization", "Basic "+ apiKey);
        httpHeaders.set("Content-Type", "application/json");

        HttpEntity<NotificationDto> requestBody = new HttpEntity<>(notificationDto,httpHeaders);

        ResponseEntity<?> response = restTemplate.postForEntity(
                "https://onesignal.com/api/v1/notifications",
                requestBody,
                Map.class);
        System.out.println(response.getBody());
        System.out.println(apiKey);

        return false;
    }
}
