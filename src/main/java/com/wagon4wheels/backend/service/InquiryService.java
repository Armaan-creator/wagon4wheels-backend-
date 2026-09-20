package com.wagon4wheels.backend.service;

import com.wagon4wheels.backend.dto.InquiryRequest;
import com.wagon4wheels.backend.exception.ResourceNotFoundException;
import com.wagon4wheels.backend.model.Inquiry;
import com.wagon4wheels.backend.repository.InquiryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    public InquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    public Inquiry create(InquiryRequest request) {
        Inquiry inquiry = new Inquiry();
        inquiry.setName(request.getName());
        inquiry.setEmail(request.getEmail());
        inquiry.setPhone(request.getPhone());
        inquiry.setReason(request.getReason());
        inquiry.setMessage(request.getMessage());
        inquiry.setRead(false);
        return inquiryRepository.save(inquiry);
    }

    public List<Inquiry> getAll() {
        return inquiryRepository.findAll();
    }

    public Inquiry setRead(String id, boolean read) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inquiry not found: " + id));
        inquiry.setRead(read);
        return inquiryRepository.save(inquiry);
    }

    public void delete(String id) {
        if (!inquiryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inquiry not found: " + id);
        }
        inquiryRepository.deleteById(id);
    }
}
