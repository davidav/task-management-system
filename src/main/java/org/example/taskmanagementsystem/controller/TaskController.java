package org.example.taskmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.dto.Result;
import org.example.taskmanagementsystem.dto.StatusCode;
import org.example.taskmanagementsystem.dto.task.TaskRqToTaskConvertor;
import org.example.taskmanagementsystem.dto.task.TaskRs;
import org.example.taskmanagementsystem.dto.task.TaskToTaskRsConvertor;
import org.example.taskmanagementsystem.entity.Task;
import org.example.taskmanagementsystem.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;
    private final TaskToTaskRsConvertor taskToTaskRsConvertor;

    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id) {
        Task taskById = taskService.findById(id);
        TaskRs taskRs = taskToTaskRsConvertor.convert(taskById);
        return new Result(true, StatusCode.SUCCESS, "Find one success", taskById);
    }



}
