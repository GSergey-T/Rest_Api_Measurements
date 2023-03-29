package com.example.demo.DTO.MeasurementDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MeasurementDTOCountRainingDay {

    //@JsonProperty(value = "CountRainingDay")
    Integer countRainingDay;

}
