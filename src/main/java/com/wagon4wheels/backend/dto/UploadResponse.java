package com.wagon4wheels.backend.dto;

import java.util.List;

public class UploadResponse {
    private final List<String> urls;

    public UploadResponse(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getUrls() {
        return urls;
    }
}
