package com.victor.hireFlow.service.impl;

import com.victor.hireFlow.dto.UserResponse;
import com.victor.hireFlow.entity.User;
import com.victor.hireFlow.repository.UserRepository;
import com.victor.hireFlow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        if (emailExists(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userDetails.getName() != null) {
            user.setName(userDetails.getName());
        }
        if (userDetails.getPassword() != null) {
            user.setPassword(userDetails.getPassword());
        }

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // Métodos para UserResponse (DTO sem senha)
    @Override
    public UserResponse getUserResponseById(Long id) {
        Optional<User> user = getUserById(id);
        return user.map(this::convertToUserResponse).orElse(null);
    }

    @Override
    public UserResponse getUserResponseByEmail(String email) {
        Optional<User> user = getUserByEmail(email);
        return user.map(this::convertToUserResponse).orElse(null);
    }

    @Override
    public List<UserResponse> getAllUserResponses() {
        return getAllUsers().stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse createUserResponse(User user) {
        User createdUser = createUser(user);
        return convertToUserResponse(createdUser);
    }

    // Método auxiliar para conversão
    private UserResponse convertToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }
}
