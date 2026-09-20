package com.wagon4wheels.backend.controller;

import com.wagon4wheels.backend.dto.InquiryRequest;
import com.wagon4wheels.backend.dto.ReadStatusRequest;
import com.wagon4wheels.backend.model.Inquiry;
import com.wagon4wheels.backend.service.InquiryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {

    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Inquiry create(@Valid @RequestBody InquiryRequest request) {
        return inquiryService.create(request);
    }

    @GetMapping
    public List<Inquiry> getAll() {
        return inquiryService.getAll();
    }

    @PatchMapping("/{id}")
    public Inquiry updateReadStatus(@PathVariable String id, @RequestBody ReadStatusRequest request) {
        return inquiryService.setRead(id, request.isRead());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        inquiryService.delete(id);
    }
}
