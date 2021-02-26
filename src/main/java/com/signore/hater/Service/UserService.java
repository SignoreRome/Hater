package com.signore.hater.Service;

import com.signore.hater.Entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    User save(User user);
}
