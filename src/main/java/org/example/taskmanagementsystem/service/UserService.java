package org.example.taskmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.FormattedMessage;
import org.example.taskmanagementsystem.entity.User;
import org.example.taskmanagementsystem.repo.UserRepository;
import org.example.taskmanagementsystem.security.AppUserDetails;
import org.example.taskmanagementsystem.util.AppHelperUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
@Transactional
@ResponseBody
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findById(id)
                .map(existedUser -> {
                    //  If the user id not admin, then user can only update own username
                    if (authentication.getAuthorities().stream()
                            .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))){
                        update.setEmail(null);
                        update.setRoles(null);
                        update.setPassword(null);
                    }

                    AppHelperUtils.copyNonNullProperties(update, existedUser);
                    return userRepository.save(existedUser);
                })
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
    }

    public void deleteById(Long id) {
        userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user not found"));

        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(AppUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(
                        MessageFormatter.format("User with userName {} not found", username).getMessage()
                ));
    }
}
