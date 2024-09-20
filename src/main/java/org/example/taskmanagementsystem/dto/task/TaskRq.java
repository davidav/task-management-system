package org.example.taskmanagementsystem.dto.task;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.example.taskmanagementsystem.entity.Priority;
import org.example.taskmanagementsystem.entity.Status;

public record TaskRq(
        @Size(min = 3, max = 20, message = "Title must be from {min} to {max}")
        String title,
        @Size(min = 5, max = 50, message = "Description must be from {min} to {max}")
        String description,
        @NotNull(message = "Status must not be null")
        Status status,
        @NotNull(message = "Priority must not be null")
        Priority priority,
        @Positive(message = "Author id must be > 0")
        Long authorId,
        @Positive(message = "Assignee id must be > 0")
        Long assigneeId) {
}

