package com.example.demo.Controllers;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Models.User;
import com.example.demo.Services.RegistrationService;
import com.example.demo.Utils.Errors.UserErrorResponce;
import com.example.demo.Utils.Exceptions.UserCreatedException;
import com.example.demo.Utils.Validations.UserValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ModelMapper modelMapper;
    private final RegistrationService registrationService;
    private final PasswordEncoder passwordEncoder;
    private final UserValidation userValidation;

    @Autowired
    public AuthController(ModelMapper modelMapper, RegistrationService registrationService, PasswordEncoder passwordEncoder, UserValidation userValidation) {
        this.modelMapper = modelMapper;
        this.registrationService = registrationService;
        this.passwordEncoder = passwordEncoder;
        this.userValidation = userValidation;
    }

    @PostMapping("/reg")
    public ResponseEntity<HttpStatus> login(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {

        User user = modelMapper.map(userDTO, User.class);

        userValidation.validate(user,bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            for(FieldError fieldError: fieldErrorList) {
                errorMessage.append(fieldError.getField())
                        .append(" -> ")
                        .append(fieldError.getDefaultMessage())
                        .append("; ");
            }
            throw new UserCreatedException(errorMessage.toString());
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));

        registrationService.saving(user);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponce> handleException(UserCreatedException e) {
        UserErrorResponce userErrorResponce = new UserErrorResponce(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(userErrorResponce, HttpStatus.BAD_REQUEST);
    }
}
