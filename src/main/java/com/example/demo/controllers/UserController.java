package com.example.demo.controllers;

import com.example.demo.PasswordConfirmationException;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.service.CartService;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;
    private CartService cartService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(
            UserService userService,
            CartService cartService,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.cartService = cartService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        if (!createUserRequest.getPassword()
                .equalsIgnoreCase(
                        createUserRequest.getConfirmPassword())) {
            throw new PasswordConfirmationException(
                    "Password confirmation failed. Please provide correct input");
        }

        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
        user.setCart(cartService.save(new Cart()));

        return ResponseEntity.ok(userService.save(user));
    }
}
