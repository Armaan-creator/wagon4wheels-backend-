package com.wagon4wheels.backend.repository;

import com.wagon4wheels.backend.model.Inquiry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InquiryRepository extends MongoRepository<Inquiry, String> {
}
