package org.example.taskmanagementsystem.dto.comment;

import org.example.taskmanagementsystem.entity.Comment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommentRqToCommentConverter implements Converter<CommentRq, Comment> {
    @Override
    public Comment convert(CommentRq source) {

        return Comment.builder()
                .comment(source.comment())
                .author(null) //TODO (userService.findById(source.authorId()))
                .task(null) //TODO (taskService.findById(source.taskId()))
                .build();
    }
}
