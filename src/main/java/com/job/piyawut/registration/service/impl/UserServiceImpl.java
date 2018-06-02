package com.job.piyawut.registration.service.impl;

import static com.job.piyawut.registration.constant.Constants.GOLD_MEMBER_LIMIT;
import static com.job.piyawut.registration.constant.Constants.PLATINUM_MEMBER_LIMIT;
import static com.job.piyawut.registration.constant.Constants.SILVER_MEMBER_LIMIT;
import com.job.piyawut.registration.constant.ErrorCode;
import com.job.piyawut.registration.constant.MemberType;
import com.job.piyawut.registration.exception.DataNotFoundException;
import com.job.piyawut.registration.exception.RegistrationException;
import com.job.piyawut.registration.model.User;
import com.job.piyawut.registration.repository.UserRepository;
import com.job.piyawut.registration.service.UserService;
import com.job.piyawut.registration.utils.DateUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)  {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());

    }

    @Override
    public User findByUserName(String userName) throws DataNotFoundException {
        final User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "User not found");
        }
        return user;
    }

    @Override
    public User create(User user) throws RegistrationException {
        // check duplicated user
        final User existedUser = userRepository.findByUsername(user.getUsername());
        if (existedUser != null) {
            throw new RegistrationException(ErrorCode.DUPLICATE_USERNAME, "User already exists");
        }
        // set member typ clasify from salary 
        classifyMemberTypeBySalary(user);

        // generate ref code
        generateReferenceCode(user);

        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        final List<User> users = new ArrayList<>();
        userRepository.findAll()
                .iterator().forEachRemaining(users::add);

        return users;
    }

    @Override
    public void delete(String userName) throws DataNotFoundException {
        final User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "User not found");
        }

        userRepository.delete(user);

    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    private void classifyMemberTypeBySalary(User user) throws RegistrationException {
        if (user.getSalary() >= PLATINUM_MEMBER_LIMIT) {
            user.setType(MemberType.Platinum);
        } else if (user.getSalary() >= GOLD_MEMBER_LIMIT) {
            user.setType(MemberType.Gold);
        } else if (user.getSalary() >= SILVER_MEMBER_LIMIT) {
            user.setType(MemberType.Silver);
        } else {
            throw new RegistrationException(ErrorCode.VALIDATION_ERROR, "The salary is less than prescribed condition");
        }

    }

    private void generateReferenceCode(User user) {
        final String phone = user.getPhone();
        final String last4Digits = phone.substring(phone.length() - 4);

        final String referCode = DateUtils.currentDate() + last4Digits;

        user.setRefCode(referCode);

    }
}
