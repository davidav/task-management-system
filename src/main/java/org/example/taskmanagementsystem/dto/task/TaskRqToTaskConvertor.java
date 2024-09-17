package org.example.taskmanagementsystem.dto.task;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.entity.Task;
import org.example.taskmanagementsystem.service.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskRqToTaskConvertor implements Converter<TaskRq, Task> {

    private final UserService userService;

    @Override
    public Task convert(TaskRq source) {
        return Task.builder()
                .title(source.title())
                .description(source.description())
                .status(source.status())
                .priority(source.priority())
                .author(userService.findById(source.authorId()))
                .assignee(userService.findById(source.assigneeId()))
                .build();
    }
}
