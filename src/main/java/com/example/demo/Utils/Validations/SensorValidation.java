package com.example.demo.Utils.Validations;

import com.example.demo.Models.Sensor;
import com.example.demo.Services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidation implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidation(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Sensor.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Sensor sensor = (Sensor) o;
        if (sensorService.validator(sensor.getName()) != null)
            errors.rejectValue("name", "", "Такой сенсор уже зарегистрирован в базе");
    }
}
