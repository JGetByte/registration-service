package com.job.piyawut.registration.controller;

import com.job.piyawut.registration.exception.DataNotFoundException;
import com.job.piyawut.registration.model.User;
import com.job.piyawut.registration.service.UserService;
import static com.job.piyawut.registration.UserTestUtil.createTestUser;
import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.core.Is.is;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Piyawut Chiradejnunt<pchiradejnunt@gmail.com>
 */
@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(secure = false, controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final String FIND_BY_USERNAME_API = "/users/{username}";

    @Test
    public void shouldReturnOkStatusWhenFindByUserName() throws DataNotFoundException, Exception {
        final User user = createTestUser();
        when(userService.findByUserName(anyString())).thenReturn(user);

        final ResultActions result = mockMvc.perform(get(FIND_BY_USERNAME_API, "testUser"));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.username", is("testUser")));
    }

}
