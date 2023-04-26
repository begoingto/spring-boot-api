package com.begoingto.springbootapi.api.user.web;

public record UserDto(
        String name,
        String gender,
        String studentCardId,
        Boolean isStudent
) {
}
