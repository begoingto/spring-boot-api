package com.begoingto.springbootapi.api.file;

public record FileDto(
        String name,
        String url,
        String extention,
        long size
){}
