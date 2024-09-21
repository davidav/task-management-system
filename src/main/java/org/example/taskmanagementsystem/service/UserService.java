package org.example.taskmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.entity.User;
import org.example.taskmanagementsystem.repo.UserRepository;
import org.example.taskmanagementsystem.util.AppHelperUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
@Transactional
@ResponseBody
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                MessageFormatter.format("User with id {} not found", id).getMessage()));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(Long id, User update) {

        return userRepository.findById(id)
                .map(existedUser -> {
                    AppHelperUtils.copyNonNullProperties(update, existedUser);
                    return userRepository.save(existedUser);
                })
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
    }

    public void deleteById(Long id) {
        userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user not found"));
        userRepository.deleteById(id);
    }
}
