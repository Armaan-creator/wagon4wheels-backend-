package com.wagon4wheels.backend.repository;

import com.wagon4wheels.backend.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarRepository extends MongoRepository<Car, String> {
}
