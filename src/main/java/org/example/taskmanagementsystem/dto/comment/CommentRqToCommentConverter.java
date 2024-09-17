package org.example.taskmanagementsystem.dto.comment;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.entity.Comment;
import org.example.taskmanagementsystem.service.TaskService;
import org.example.taskmanagementsystem.service.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentRqToCommentConverter implements Converter<CommentRq, Comment> {

    private final TaskService taskService;
    private final UserService userService;

    @Override
    public Comment convert(CommentRq source) {

        return Comment.builder()
                .comment(source.comment())
                .author(userService.findById(source.authorId()))
                .task(taskService.findById(source.taskId()))
                .build();
    }
}
