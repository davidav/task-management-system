package org.example.taskmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.dto.Result;
import org.example.taskmanagementsystem.dto.StatusCode;
import org.example.taskmanagementsystem.dto.task.TaskRq;
import org.example.taskmanagementsystem.dto.task.TaskRqToTaskConvertor;
import org.example.taskmanagementsystem.dto.task.TaskToTaskRsConvertor;
import org.example.taskmanagementsystem.entity.Task;
import org.example.taskmanagementsystem.service.TaskService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;
    private final TaskToTaskRsConvertor taskToTaskRsConvertor;
    private final TaskRqToTaskConvertor taskRqToTaskConvertor;




    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id) {

        return new Result(true, StatusCode.SUCCESS, "Find one success",
                taskToTaskRsConvertor.convert(taskService.findById(id)));
    }


    @PostMapping
    public Result createTaskSuccess(@RequestBody @Valid TaskRq rq){

        Task createdTask = taskService.create(taskRqToTaskConvertor.convert(rq));

        return new Result(true,StatusCode.SUCCESS, "Task created",
                taskToTaskRsConvertor.convert(createdTask));

    }

}
