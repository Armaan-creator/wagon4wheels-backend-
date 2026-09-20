package com.wagon4wheels.backend.controller;

import com.wagon4wheels.backend.dto.UploadResponse;
import com.wagon4wheels.backend.service.FileStorageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {

    private final FileStorageService fileStorageService;

    public UploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping
    public UploadResponse upload(@RequestParam("files") List<MultipartFile> files) {
        return new UploadResponse(fileStorageService.store(files));
    }
}
