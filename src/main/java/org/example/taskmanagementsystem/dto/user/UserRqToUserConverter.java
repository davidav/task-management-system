package org.example.taskmanagementsystem.dto.user;

import jakarta.validation.Valid;
import org.example.taskmanagementsystem.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserRqToUserConverter implements Converter<UserRq, User> {
    public User convert(@Valid UserRq rq) {

        return User.builder()
                .username(rq.username())
                .password(rq.password())
                .email(rq.email())
                .roles(rq.roles())
                .build();

    }
}
