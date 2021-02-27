package com.signore.hater.Service;

import com.signore.hater.Entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    User save(User user);
    List<User> getUsers();
    boolean addUser(User user);
    boolean activateUser(String code);
}
