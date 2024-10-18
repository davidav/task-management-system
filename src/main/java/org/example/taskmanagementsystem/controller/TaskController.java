package org.example.taskmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.dto.Result;
import org.example.taskmanagementsystem.dto.StatusCode;
import org.example.taskmanagementsystem.dto.task.*;
import org.example.taskmanagementsystem.entity.Task;
import org.example.taskmanagementsystem.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.endpoint.base-url}/task")
public class TaskController {
    private final TaskService taskService;
    private final TaskToTaskRsConvertor taskToTaskRsConvertor;
    private final TaskRqToTaskConvertor taskRqToTaskConvertor;

    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id) {

        return new Result(true, StatusCode.SUCCESS, "Found one",
                taskToTaskRsConvertor.convert(taskService.findById(id)));
    }

    @GetMapping
    public Result findAll(Pageable pageable) {

        Page<Task> tasksPage = taskService.findAll(pageable);
        Page<TaskRs> taskRsPage = tasksPage.
                map(taskToTaskRsConvertor::convert);

        return new Result(true, StatusCode.SUCCESS, "Found all", taskRsPage);
    }

    @PostMapping
    public Result createTask(@RequestBody @Valid TaskRq rq) {

        Task createdTask = taskService.create(taskRqToTaskConvertor.convert(rq));

        return new Result(true, StatusCode.SUCCESS, "Task created",
                taskToTaskRsConvertor.convert(createdTask));

    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Long id, @RequestBody @Valid TaskRq rq) {

        Task convert = taskRqToTaskConvertor.convert(rq);
        Task task = taskService.update(id, convert);

        return new Result(true, StatusCode.SUCCESS, "Update success",
                taskToTaskRsConvertor.convert(task));
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id) {
        taskService.deleteById(id);
        return new Result(true, StatusCode.SUCCESS, "Delete success");
    }

    @GetMapping("/filter")
    public Result findAllByFilter(@Valid TaskFilter filter) {

        Page<Task> taskPage = taskService.filterBy(filter);
        Page<TaskRs> taskRsPage = taskPage.map(taskToTaskRsConvertor::convert);
        return new Result(true, StatusCode.SUCCESS, "Filtered tasks", taskRsPage);
    }

    @PostMapping("/search")
    public Result findTasksByCriteria(@RequestBody Map<String, String> searchCriteria, Pageable pageable) throws IllegalAccessException {
        Page<Task> taskPage = taskService.findByCriteria(searchCriteria, pageable);
        Page<TaskRs> taskRsPage = taskPage.map(taskToTaskRsConvertor::convert);
        return new Result(true, StatusCode.SUCCESS, "Search result", taskRsPage);
    }
}
