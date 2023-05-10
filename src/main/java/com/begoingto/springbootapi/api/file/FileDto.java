package com.begoingto.springbootapi.api.file;

import lombok.Builder;

@Builder
public record FileDto(
        String name,
        String url,

        String extension,
        String size,
        String downloadUrl
        ){}
