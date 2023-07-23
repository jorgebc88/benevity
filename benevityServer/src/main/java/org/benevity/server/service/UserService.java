package org.benevity.server.service;

import org.benevity.server.repository.UserRepository;
import org.benevity.server.repository.entity.User;
import org.benevity.server.service.exception.OperationNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return this.userRepository.save(newUser);
    }

    public User getUser(String userId) {
        return this.userRepository.findById(userId).orElseThrow();
    }

    public List<User> getUserList() {
        return this.userRepository.findAll();
    }

    public User updateUser(String userId, User user) {
        user.setId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    public void deleteUser(String userId, String userLoggedId) {
        if (userId.equals(userLoggedId)) {
            throw new OperationNotAllowedException("You can not delete the user logged.");
        }
        this.userRepository.deleteById(userId);
    }
}
