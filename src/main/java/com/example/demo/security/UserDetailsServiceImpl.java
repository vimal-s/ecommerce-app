package com.example.demo.security;

import com.example.demo.model.persistence.User;
import com.example.demo.service.UserService;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;

    public UserDetailsServiceImpl(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user from the database with username: " + username);

        User user = userService.getUser(username);

        return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
