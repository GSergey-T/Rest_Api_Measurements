package com.example.demo.Controllers;

import com.example.demo.DTO.MeasurementDTO.MeasurementDTOForRequest;
import com.example.demo.DTO.MeasurementDTO.MeasurementDTOCountRainingDay;
import com.example.demo.DTO.MeasurementDTO.MeasurementDTOForResponce;
import com.example.demo.Models.Measurement;
import com.example.demo.Services.MeasurementService;
import com.example.demo.Utils.Exceptions.MeasurementCreatedException;
import com.example.demo.Utils.Errors.MeasurementErrorResponce;
import com.example.demo.Utils.Validations.MeasurementValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementContrloller {

    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final MeasurementValidation measurementValidation;

    @Autowired
    public MeasurementContrloller(MeasurementService measurementService, ModelMapper modelMapper, MeasurementValidation measurementValidation) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidation = measurementValidation;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement (@RequestBody @Valid MeasurementDTOForRequest measurementDTOForRequest,
                                                      BindingResult bindingResult) {

        Measurement measurement = convertToMeasurement(measurementDTOForRequest);

        //Проверка на наличие зарегистрированного сенсора в базе
        measurementValidation.validate(measurement,bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            for(FieldError fieldError: fieldErrorList) {
                errorMessage.append(fieldError.getField())
                        .append(" -> ")
                        .append(fieldError.getDefaultMessage())
                        .append("; ");
            }
            throw new MeasurementCreatedException(errorMessage.toString());
        }

        measurement.setSensor(measurementService.getSensorByName(measurementDTOForRequest.getSensor().getName()));
        measurement.setTime(LocalDateTime.now());
        measurementService.add(measurement);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping()
    public List<MeasurementDTOForResponce> allMeasurement() {
        return measurementService.getAllMeasurement().stream()
                .map(m -> { return new MeasurementDTOForResponce(
                        m.getValue(),
                        m.isRaining(),
                        m.getTime(),
                        m.getSensor().getName()
                );})
                .collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public Object rainyDaysCount() {
        return new MeasurementDTOCountRainingDay(measurementService.getRainingDay());
    }

    private Measurement convertToMeasurement(MeasurementDTOForRequest measurementDTOForRequest) {
        return modelMapper.map(measurementDTOForRequest,Measurement.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponce> handleException(MeasurementCreatedException e) {
        MeasurementErrorResponce measurementErrorResponce = new MeasurementErrorResponce(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(measurementErrorResponce, HttpStatus.BAD_REQUEST);
    }

}
