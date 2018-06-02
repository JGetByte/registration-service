package com.job.piyawut.registration.service;

import com.job.piyawut.registration.exception.DataNotFoundException;
import com.job.piyawut.registration.exception.RegistrationException;
import com.job.piyawut.registration.model.User;
import java.util.List;

public interface UserService {

    public User findByUserName(String userName) throws DataNotFoundException;

    public User create(User user) throws RegistrationException;

    public List<User> findAll();

    public void delete(String userName) throws DataNotFoundException;
}
