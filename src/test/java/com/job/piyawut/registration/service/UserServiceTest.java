package com.job.piyawut.registration.service;

import com.job.piyawut.registration.exception.DataNotFoundException;
import com.job.piyawut.registration.model.User;
import com.job.piyawut.registration.repository.UserRepository;
import com.job.piyawut.registration.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import com.job.piyawut.registration.constant.MemberType;
import com.job.piyawut.registration.exception.RegistrationException;
import static com.job.piyawut.registration.UserTestUtil.createTestUser;
import com.job.piyawut.registration.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;

/**
 *
 * @author Piyawut Chiradejnunt<pchiradejnunt@gmail.com>
 */
@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User mockUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl();
        userService.setUserRepository(userRepository);
    }

    // --- test find user
    @Test
    public void shouldBeFoundUser() throws DataNotFoundException {
        when(userRepository.findByUsername(anyString())).thenReturn(mockUser);

        final User user = userService.findByUserName(anyString());

        assertThat(user, is(equalTo(user)));
    }

    @Test(expected = DataNotFoundException.class)
    public void shouldThrowDataNotFoundExceptionWhenFindByUsername() throws DataNotFoundException {
        when(userRepository.findByUsername("testUser")).thenReturn(mockUser);

        userService.findByUserName("unknowUser");
    }

    //-- test create user
    @Test
    public void shouldCreateUserSuccess() throws RegistrationException {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        final User testUser = createTestUser();
        userService.create(testUser);
    }

    @Test
    public void shouldCreateUserSuccessWithVerifyOneTime() throws RegistrationException {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        final User testUser = createTestUser();
        userService.create(testUser);

        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    public void shouldCreateUserSuccessWithPlatinumMember() throws RegistrationException {
        final User testUser = createTestUser();
        testUser.setSalary(51000d);

        userService.create(testUser);
        assertThat(testUser.getType(), is(equalTo(MemberType.Platinum)));
    }

    @Test
    public void shouldCreateUserSuccessWithGoldMember() throws RegistrationException {
        final User testUser = createTestUser();
        testUser.setSalary(35000d);

        userService.create(testUser);
        assertThat(testUser.getType(), is(equalTo(MemberType.Gold)));
    }

    @Test
    public void shouldCreateUserSuccessWithSilverMember() throws RegistrationException {
        final User testUser = createTestUser();
        testUser.setSalary(25000d);

        userService.create(testUser);
        assertThat(testUser.getType(), is(equalTo(MemberType.Silver)));
    }

    @Test
    public void shouldCreateUserSuccessWithGenerateReferCode() throws RegistrationException {
        final String testUserPhone = "0893001234";

        final User testUser = createTestUser();
        testUser.setPhone(testUserPhone);
        userService.create(testUser);

        final String expectedCode = DateUtils.currentDate()
                + testUserPhone.substring(testUserPhone.length() - 4);

        assertThat(testUser.getRefCode(), is(equalTo(expectedCode)));
        assertThat(testUser.getRefCode().length(), is(equalTo(expectedCode.length())));
    }

    @Test(expected = RegistrationException.class)
    public void shouldRejectedBySalaryLowerWhenCreateUser() throws RegistrationException {
        final User testUser = createTestUser();
        testUser.setSalary(10000d);

        userService.create(testUser);
    }

    @Test(expected = RegistrationException.class)
    public void shouldThrowRegistrationExceptiondByDuplicateWhenCreateUser() throws RegistrationException {
        when(userRepository.findByUsername(anyString())).thenReturn(mockUser);
        final User testUser = createTestUser();

        userService.create(testUser);
    }

    //-- test delete user
    @Test
    public void shouldDeleteUserSuccess() throws DataNotFoundException {
        when(userRepository.findByUsername(anyString())).thenReturn(mockUser);
        doNothing().when(userRepository).delete(mockUser);

        userService.delete(anyString());
    }

    @Test
    public void shouldVerifyOneTimeWhenDeleteUser() throws DataNotFoundException {
        when(userRepository.findByUsername(anyString())).thenReturn(mockUser);

        doNothing().when(userRepository).delete(mockUser);
        userService.delete(anyString());

        verify(userRepository, times(1)).delete(mockUser);
    }

    @Test(expected = DataNotFoundException.class)
    public void shouldThrowDataNotFoundWhenDeleteUser() throws DataNotFoundException {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        Mockito.doNothing().when(userRepository).delete(mockUser);

        final User testUser = createTestUser();
        userService.delete(testUser.getUsername());
    }

}
