package org.example.taskmanagementsystem.dto.task;

import org.example.taskmanagementsystem.entity.Priority;
import org.example.taskmanagementsystem.entity.Status;

public record TaskRq(String title,
                     String description,
                     Status status,
                     Priority priority,
                     Long authorId,
                     Long assigneeId) {
}

