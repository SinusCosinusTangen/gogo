package com.bigone.spring.gogo.service;

import com.bigone.spring.gogo.entity.User;
import com.bigone.spring.gogo.enumerate.Role;
import com.bigone.spring.gogo.exception.DuplicateDataException;
import com.bigone.spring.gogo.exception.NotFoundException;
import com.bigone.spring.gogo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername("test");
    }

    @Override
    public Mono<User> createUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.USER);

        return userRepository.findByUsernameOrEmail(username, email)
                .flatMap(existingUser -> Mono.error(new DuplicateDataException("Username/email already exists: %s/%s".formatted(username, email))))
                .cast(User.class)
                .switchIfEmpty(userRepository.save(user)
                        .flatMap(Mono::just));
    }

    @Override
    public Mono<User> changePassword(String username, String email, String password) {
        return userRepository.findByUsernameOrEmail(username, email)
                .flatMap(user -> {
                    user.setPassword(password);
                    userRepository.save(user)
                            .flatMap(Mono::just);
                    return Mono.just(user);
                })
                .switchIfEmpty(Mono.error(new NotFoundException("username/email not found: %s/%s".formatted(username, email))));
    }
}
