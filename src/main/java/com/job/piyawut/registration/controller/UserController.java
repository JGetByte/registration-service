package com.job.piyawut.registration.controller;

import com.job.piyawut.registration.exception.DataNotFoundException;
import com.job.piyawut.registration.model.User;
import com.job.piyawut.registration.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Piyawut Chiradejnunt<pchiradejnunt@gmail.com>
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public List<User> getUsers()
            throws DataNotFoundException {
        return userService.findAll();
    }

    @GetMapping(
            value = "users/{username}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(
            @PathVariable(name = "username") String username)
            throws DataNotFoundException {
        return userService.findByUserName(username);
    }

    @DeleteMapping(value = "/users/{username}")
    public String deleteUser(
            @PathVariable(name = "username") String username)
            throws DataNotFoundException {
        userService.delete(username);
        return "success";
    }

}
