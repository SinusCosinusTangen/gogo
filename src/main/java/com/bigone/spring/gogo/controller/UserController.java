package com.bigone.spring.gogo.controller;

import com.bigone.spring.gogo.entity.User;
import com.bigone.spring.gogo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @MutationMapping
    public Mono<User> createUser(@Argument String username, @Argument String email, @Argument String password) {
        return userService.createUser(username, email, password);
    }

    @MutationMapping
    public Mono<User> findUserByUsername(@Argument String username) {
        return userService.findByUsername(username);
    }

    @MutationMapping
    public Mono<User> changePassword(@Argument String username, @Argument String email, @Argument String password) {
        return userService.changePassword(username, email, password);
    }
}