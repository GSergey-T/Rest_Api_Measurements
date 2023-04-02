package com.example.demo.Utils.Validations;

import com.example.demo.Models.Sensor;
import com.example.demo.Models.User;
import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidation implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidation(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Sensor.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (userService.validator(user.getName()).isPresent())
            errors.rejectValue("name", "", "Такой пользователь уже зарегистрирован в базе");
    }
}
