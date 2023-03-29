package com.example.demo.Services;

import com.example.demo.Models.Sensor;
import com.example.demo.Repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void registration(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    public Sensor validator(String name){
        return sensorRepository.findByName(name);
    }



}
