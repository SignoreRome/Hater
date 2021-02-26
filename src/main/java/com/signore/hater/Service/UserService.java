package com.signore.hater.Service;

import com.signore.hater.Entity.User;

public interface UserService {
    User findByUsername(String username);
    User save(User user);
}
