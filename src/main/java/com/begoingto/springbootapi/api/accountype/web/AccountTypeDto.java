package com.begoingto.springbootapi.api.accountype.web;

import lombok.Builder;

@Builder
public record AccountTypeDto(
        Integer id,
        String name
) {}