package com.job.piyawut.registration.controller;

import com.job.piyawut.registration.exception.RegistrationException;
import com.job.piyawut.registration.model.User;
import com.job.piyawut.registration.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Piyawut Chiradejnunt<pchiradejnunt@gmail.com>
 */
@Slf4j
@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/greeting")
    public String greeting() {
        log.info("##############");
        return "hello guys!!";
    }

    @PostMapping("/signup")
    @ResponseStatus(value = HttpStatus.CREATED)
    public User createUser(@RequestBody User user) throws RegistrationException {
        return userService.create(user);
    }
}
