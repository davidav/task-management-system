package org.example.taskmanagementsystem.dto.task;

import org.example.taskmanagementsystem.dto.comment.CommentRs;
import org.example.taskmanagementsystem.entity.Priority;
import org.example.taskmanagementsystem.entity.Status;

import java.time.Instant;
import java.util.List;

public record TaskRs(Long id,
                     String title,
                     String description,
                     Status status,
                     Priority priority,
                     Long authorId,
                     Long assigneeId,
                     Instant createdAt,
                     List<CommentRs> commentsRs){

}

