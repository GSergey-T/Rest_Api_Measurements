package com.example.demo.Services;

import com.example.demo.Models.Measurement;
import com.example.demo.Models.Sensor;
import com.example.demo.Repositories.MeasurementRepository;
import com.example.demo.Repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void add(Measurement measurement) {
        measurementRepository.save(measurement);
    }

    public Sensor getSensorByName(String name) {
        return sensorRepository.findByName(name);
    }

    public List<Measurement> getAllMeasurement() {
        return measurementRepository.findAll();
    }

    public int getRainingDay() {
        return measurementRepository.findByRainingIsTrue().size();
    }

}
