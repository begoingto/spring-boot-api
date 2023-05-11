package com.begoingto.springbootapi.api.notification.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.Builder;

@Builder
public record NotificationDto(
   @JsonProperty("included_segments") String[] includedSegments,
   ContentDto contents,
   @JsonProperty("app_id") @Nullable String appId
) {}
