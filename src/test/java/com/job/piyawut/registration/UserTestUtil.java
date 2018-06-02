package com.job.piyawut.registration;

import com.job.piyawut.registration.model.User;

/**
 *
 * @author Piyawut Chiradejnunt<pchiradejnunt@gmail.com>
 */
public class UserTestUtil {
    public final static String TEST_USER = "testUser";
    public final static String TEST_PASSWORD = "password";
    public final static String TEST_PHONE = "0891123431";
    public final static Double TEST_SALARY = 25000d;
    
    public static User createTestUser() {
        User user = new User();
        user.setId(1);
        user.setUsername(TEST_USER);
        user.setPassword(TEST_PASSWORD);
        user.setPhone(TEST_PHONE);
        user.setSalary(TEST_SALARY);

        return user;
    }
}
