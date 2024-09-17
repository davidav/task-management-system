package org.example.taskmanagementsystem.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.taskmanagementsystem.dto.task.TaskFilter;

public class TaskFilterValidValidator implements ConstraintValidator<TaskFilterValid, TaskFilter> {
    @Override
    public boolean isValid(TaskFilter value, ConstraintValidatorContext context) {

        return value.getPageNumber() != null && value.getPageSize() != null;
    }
}
