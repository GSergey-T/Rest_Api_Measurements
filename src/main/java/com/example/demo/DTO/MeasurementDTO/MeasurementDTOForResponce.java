package com.example.demo.DTO.MeasurementDTO;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDTOForResponce {

    //@JsonProperty(value = "Value")
    float value;
    //@JsonProperty(value = "Raining")
    Boolean raining;
    //@JsonProperty(value = "Time")
    LocalDateTime time;
    //@JsonProperty(value = "Sensor")
    String sensor;

}
