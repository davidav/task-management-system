package org.example.taskmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    @ToString.Exclude
    private User author;

    @CreationTimestamp
    private Instant createAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    @ToString.Exclude
    private Task task;

    public Long getAuthorId() {
        return author != null
                ? author.getId()
                : null;
    }

    public Long getTaskId() {
        return task != null
                ? task.getId()
                : null;
    }
}




