package com.begoingto.springbootapi.api.file;

import jakarta.annotation.Nullable;

public record FileDto(
        String name,
        String url,

        String extension,
        String size,
        String downloadUrl
        ){}
