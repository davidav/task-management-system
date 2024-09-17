package org.example.taskmanagementsystem.dto.user;


import org.example.taskmanagementsystem.entity.RoleType;

import java.util.Set;

public record UserRs(Long id,
                     String username,
                     String email,
                     Set<RoleType> roles) {
}
