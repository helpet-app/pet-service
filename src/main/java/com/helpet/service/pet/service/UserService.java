package com.helpet.service.pet.service;

import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.pet.service.error.NotFoundLocalizedError;
import com.helpet.service.pet.store.model.User;
import com.helpet.service.pet.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean userExists(UUID userId) {
        return userRepository.existsById(userId);
    }

    public User getUser(UUID userId) throws NotFoundLocalizedException {
        return userRepository.findUserById(userId)
                             .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_EXIST));
    }
}
