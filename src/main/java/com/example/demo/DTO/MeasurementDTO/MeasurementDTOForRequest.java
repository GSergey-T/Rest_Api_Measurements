package com.example.demo.DTO.MeasurementDTO;

import com.example.demo.Models.Sensor;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MeasurementDTOForRequest {

    @NotNull(message="Значение не должно быть пустым")
    @DecimalMin(value = "-100.0", message = "Значение не менее -100")
    @DecimalMax(value = "100.0", message = "Значение не более 100")
    float value;

    @NotNull(message="Значение не должно быть пустым")
    Boolean raining;

    Sensor sensor;
}
