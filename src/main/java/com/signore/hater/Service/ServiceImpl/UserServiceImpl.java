package com.signore.hater.Service.ServiceImpl;

import com.signore.hater.Entity.Role;
import com.signore.hater.Entity.User;
import com.signore.hater.Repository.UserRepository;
import com.signore.hater.Service.MailSender;
import com.signore.hater.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service("UserService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private MailSender mailSender;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean addUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(user);

        if (!user.getEmail().isEmpty()){
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Hater. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()

            );
            mailSender.send(user.getEmail(), "Activation code", message);
        }

        return true;
    }

    @Override
    public boolean activateUser(String code) {

        User user = userRepository.findByActivationCode(code);

        if (user == null){
            return false;
        }

        user.setActivationCode(null);

        userRepository.save(user);

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
