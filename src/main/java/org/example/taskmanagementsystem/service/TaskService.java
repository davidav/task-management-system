package org.example.taskmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.dto.task.TaskFilter;
import org.example.taskmanagementsystem.entity.Task;
import org.example.taskmanagementsystem.repo.TaskRepository;
import org.example.taskmanagementsystem.repo.TaskSpecification;
import org.example.taskmanagementsystem.util.AppHelperUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
@Transactional
@ResponseBody
@RequiredArgsConstructor
public class TaskService {

private final TaskRepository taskRepository;


    public Task findById(Long id) {

        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                MessageFormatter.format("Task with id {} not found", id).getMessage()));
    }

    public List<Task> findAll() {

        return taskRepository.findAll();
    }

    public Task create(Task task) {

        return taskRepository.save(task);
    }

    public Task update(Long id, Task update) {
        return taskRepository.findById(id)
                .map(existedComment -> {
                    AppHelperUtils.copyNonNullProperties(update, existedComment);
                    return taskRepository.save(existedComment);
                })
                .orElseThrow(() -> new EntityNotFoundException("task not found"));
    }

    public void deleteById(Long id) {
        taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("task not found"));
        taskRepository.deleteById(id);
    }

    public List<Task> filterBy(TaskFilter filter) {
        Specification<Task> spec = TaskSpecification.withFilter(filter);
        PageRequest pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
        return taskRepository.findAll(spec, pageable).getContent();
    }

    public Page<Task> findAll(Pageable pageable) {

        return taskRepository.findAll(pageable);
    }
}

