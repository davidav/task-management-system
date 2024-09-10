package org.example.taskmanagementsystem.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpsertCommentRq {

    private String comment;
    private Long user_id;
    private Long task_id;


}
