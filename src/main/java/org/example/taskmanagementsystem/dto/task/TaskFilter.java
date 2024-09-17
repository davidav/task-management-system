package org.example.taskmanagementsystem.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.taskmanagementsystem.validation.TaskFilterValid;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TaskFilterValid
public class TaskFilter {

    private Integer pageSize;
    private Integer pageNumber;
    private Long authorId;
    private Long assigneeId;

}
