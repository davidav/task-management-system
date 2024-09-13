package org.example.taskmanagementsystem.dto.task;


import org.example.taskmanagementsystem.dto.comment.CommentRs;
import org.example.taskmanagementsystem.entity.Task;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;


@Component
//@RequiredArgsConstructor
public class TaskToTaskRsConvertor implements Converter<Task, TaskRs> {

//    private final CommentToCommentRsConverter commentToCommentRsConverter;

    @Override
    public TaskRs convert(Task source) {
        System.out.println("Enter to Converter");
        CommentRs commentRs = new CommentRs(1L,"Comment1", 1l, 1l, Instant.now());
        System.out.println(commentRs);
        TaskRs taskRs = new TaskRs(
                source.getId(),
                source.getTitle());
//                source.getDescription(),
//                source.getStatus(),
//                source.getPriority(),
//                source.getAuthorId(),
//                source.getAssigneeId());

        System.out.println(taskRs);
        return taskRs;
    }

//    private List<CommentRs> setCommentDtos(List<Comment> comments) {
//        return comments.stream()
//                .map(commentToCommentRsConverter::convert)
//                .collect(Collectors.toList());
//    }
}
