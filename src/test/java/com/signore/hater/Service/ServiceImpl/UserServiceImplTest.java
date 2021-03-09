package com.signore.hater.Service.ServiceImpl;

import com.signore.hater.Entity.Role;
import com.signore.hater.Entity.User;
import com.signore.hater.Repository.UserRepository;
import com.signore.hater.Service.MailSender;
import com.signore.hater.Service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository = mock(UserRepository.class);

    @Mock
    private MailSender mailSender = mock(MailSender.class);

    @Mock
    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    @InjectMocks
    private UserService userService = new UserServiceImpl(userRepository, mailSender, passwordEncoder);

    @Test
    void addUser() {
        User user = new User();
        user.setEmail("email@email.email");
        boolean isUserCreated = userService.addUser(user);
        Assert.assertTrue(isUserCreated);
        Assert.assertNotNull(user.getActivationCode());
        Assert.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        verify(userRepository, Mockito.times(1)).save(user);
        verify(mailSender, Mockito.times(1))
                .send(ArgumentMatchers.eq(user.getEmail()),
                        anyString(),
                        anyString()
                );

    }

    @Test
    void addUserFailTest() {
        User user = new User();
        user.setUsername("John");


        doReturn(new User())
                .when(userRepository)
                .findByUsername("John");

        boolean isUserCreated = userService.addUser(user);

        assertFalse(isUserCreated);

        verify(userRepository, Mockito.times(0)).save(any(User.class));
        verify(mailSender, Mockito.times(0))
                .send(anyString(),
                        anyString(),
                        anyString()
                );

    }

    @Test
    void activateUser() {
        User user = new User();

        user.setActivationCode("activate");

        doReturn(user)
                .when(userRepository)
                .findByActivationCode("activate");

        boolean isUserActivated = userService.activateUser("activate");

        assertTrue(isUserActivated);
        assertNull(user.getActivationCode());

        verify(userRepository, times(1)).save(user);

    }

    @Test
    public void activateUserFailTest(){
        boolean isUserActivated = userService.activateUser("activate me");

        assertFalse(isUserActivated);

        verify(userRepository, times(0)).save(any(User.class));

    }

}