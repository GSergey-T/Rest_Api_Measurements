package com.example.demo.Services;

import com.example.demo.Models.User;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final UserRepository userRepository;

    @Autowired
    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saving(User user) {

        if (user.getRole() == null){
            user.setRole("ROLE_USER");
        } else if (user.getRole().equalsIgnoreCase("admin")) {
            user.setRole("ROLE_ADMIN");
        } else if (user.getRole().equalsIgnoreCase("sensor")) {
            user.setRole("ROLE_SENSOR");
        }

        userRepository.save(user);
    }

}
