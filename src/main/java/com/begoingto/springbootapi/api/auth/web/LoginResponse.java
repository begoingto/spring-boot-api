package com.begoingto.springbootapi.api.auth.web;

import com.begoingto.springbootapi.api.user.User;
import lombok.Builder;

@Builder
public record LoginResponse(
        String tokenType,
        String accessToken,
        User user
) {
}
