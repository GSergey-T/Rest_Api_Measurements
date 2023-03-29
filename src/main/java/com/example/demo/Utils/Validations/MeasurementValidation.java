package com.example.demo.Utils.Validations;

import com.example.demo.Models.Measurement;
import com.example.demo.Services.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidation implements Validator {

    private final MeasurementService measurementService;

    @Autowired
    public MeasurementValidation(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Measurement.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Measurement measurement = (Measurement) o;
        if (measurementService.getSensorByName(measurement.getSensor().getName()) == null)
            errors.rejectValue("sensor", "", "Такой сенсор не зарегистрирован в базе");
    }
}
