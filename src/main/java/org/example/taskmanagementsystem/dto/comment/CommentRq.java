package org.example.taskmanagementsystem.dto.comment;

public record CommentRq(
        String comment,
        Long authorId,
        Long taskId) {
}
