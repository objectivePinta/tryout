package com.tryout.rest;

import com.tryout.entities.User;
import com.tryout.repository.UserRepository;
import com.tryout.rest.dto.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;


    @PostMapping("/register")
    public User registerUser(@RequestBody RegistrationRequest request) {
        request.setPassword(encoder.encode(request.getPassword()));
        return userRepository.save(request.toUser());
    }
}
