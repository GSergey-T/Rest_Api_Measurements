package com.example.demo.DTO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SensorDTO {

    @NotEmpty(message="Имя сенсора не должно быть пустым")
    @Size(min = 2, max = 30, message = "Длина имени должна лежать в пределах от 2 до 30")
    String name;

}
