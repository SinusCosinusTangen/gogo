package com.bigone.spring.gogo.service;

import com.bigone.spring.gogo.entity.User;
import com.bigone.spring.gogo.enumerate.Role;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> findByUsername(String username);
    Mono<User> createUser(String username, String email, String password);
    Mono<User> changePassword(String username, String email, String password);
}
