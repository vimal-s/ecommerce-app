package com.example.demo.service;

import com.example.demo.UserNotFoundException;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> {
                    logger.info("UserNotFoundException occurred");
                    return new UserNotFoundException(userId);
                });
    }

    public User getUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.info("UserNotFoundException occurred");
            throw new UserNotFoundException(username);
        }
        return user;
    }

    public User save(User user) {
        logger.info("Saving new user to database with usename: " + user.getUsername());
        return userRepository.save(user);
    }
}
