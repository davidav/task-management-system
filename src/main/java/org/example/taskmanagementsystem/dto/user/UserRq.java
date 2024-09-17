package org.example.taskmanagementsystem.dto.user;

import org.example.taskmanagementsystem.entity.RoleType;

import java.util.Set;

public record UserRq(
        String username,
        String email,
        String password,
        Set<RoleType> roles) {
}
