package com.begoingto.springbootapi.api.notification.web;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NotificationDto(
   @JsonProperty("included_segments") String[] includedSegments,
   ContentDto contents,
   @JsonProperty("app_id") String app_id
) {}
