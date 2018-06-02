package com.job.piyawut.registration.repository;

import com.job.piyawut.registration.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Piyawut Chiradejnunt<pchiradejnunt@gmail.com>
 */
public interface UserRepository extends CrudRepository<User, Long> {

    public User findByUsername(String userName);
     
}
