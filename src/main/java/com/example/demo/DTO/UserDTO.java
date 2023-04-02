package com.example.demo.DTO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {

    @Size(min = 2, max = 100, message = "Длина имени должна лежать в пределах от 2 до 100")
    @NotNull(message="Значение не должно быть пустым")
    String name;

    @NotNull(message="Значение не должно быть пустым")
    String password;

    String role;
}
