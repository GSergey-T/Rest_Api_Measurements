package com.example.demo.Controllers;

import com.example.demo.DTO.SensorDTO;
import com.example.demo.Models.Sensor;
import com.example.demo.Services.SensorService;
import com.example.demo.Utils.Exceptions.SensorCreatedException;
import com.example.demo.Utils.Errors.SensorErrorResponce;
import com.example.demo.Utils.Validations.SensorValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorValidation sensorValidation;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper, SensorValidation sensorValidation) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidation = sensorValidation;
    }

    //Регистрация нового сенсора
    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registrationSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                         BindingResult bindingResult) {

        //Проверка на уникальность имен
        sensorValidation.validate(convertToSensor(sensorDTO),bindingResult);

        //Проверка на наличие ошибок
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            for(FieldError fieldError: fieldErrorList) {
                errorMessage.append(fieldError.getField())
                            .append(" -> ")
                            .append(fieldError.getDefaultMessage())
                            .append("; ");
            }
            //Выбрасываем исключение с вложенным сообщением
            throw new SensorCreatedException(errorMessage.toString());
        }

        sensorService.registration(convertToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    //Конвертер SensorDTO -> Sensor
    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO,Sensor.class);
    }

    //Обработчик исключения
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponce> handleException(SensorCreatedException e) {
        SensorErrorResponce sersonErrorResponce = new SensorErrorResponce(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(sersonErrorResponce, HttpStatus.BAD_REQUEST);
    }
}
