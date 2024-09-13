package org.example.taskmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.entity.Task;
import org.example.taskmanagementsystem.repo.TaskRepository;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
