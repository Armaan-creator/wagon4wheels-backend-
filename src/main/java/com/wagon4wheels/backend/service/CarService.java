package com.wagon4wheels.backend.service;

import com.wagon4wheels.backend.dto.CarRequest;
import com.wagon4wheels.backend.exception.ResourceNotFoundException;
import com.wagon4wheels.backend.model.Car;
import com.wagon4wheels.backend.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public Car getById(String id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found: " + id));
    }

    public Car create(CarRequest request) {
        Car car = new Car();
        applyRequest(car, request);
        return carRepository.save(car);
    }

    public Car update(String id, CarRequest request) {
        Car car = getById(id);
        applyRequest(car, request);
        return carRepository.save(car);
    }

    public void delete(String id) {
        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException("Car not found: " + id);
        }
        carRepository.deleteById(id);
    }

    private void applyRequest(Car car, CarRequest request) {
        car.setBrand(request.getBrand());
        car.setModel(request.getModel());
        car.setDescription(request.getDescription());
        car.setOwner(request.getOwner());
        car.setPrice(request.getPrice());
        car.setKmsRun(request.getKmsRun());
        car.setFuelType(request.getFuelType());
        car.setImages(request.getImages());
    }
}
