package org.example.taskmanagementsystem.dto.task;

import org.example.taskmanagementsystem.entity.Task;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskRqToTaskConvertor implements Converter<TaskRq, Task> {

    @Override
    public Task convert(TaskRq source) {
        return Task.builder()
                .title(source.title())
                .description(source.description())
                .status(source.status())
                .priority(source.priority())
                .author(null)  //TODO (userService.findById(source.authorId()))
                .assignee(null) //TODO (userService.findById(source.assigneeId()))
                .build();
    }
}
