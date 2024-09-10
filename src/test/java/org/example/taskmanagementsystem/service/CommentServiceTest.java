package org.example.taskmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.taskmanagementsystem.dto.comment.CommentRs;
import org.example.taskmanagementsystem.dto.comment.UpsertCommentRq;
import org.example.taskmanagementsystem.entity.*;
import org.example.taskmanagementsystem.repo.CommentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentService commentService;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {

        Instant createAt = Instant.now();
        User user = User.builder()
                .id(1L)
                .username("user")
                .email("user@mail.com")
                .password("user")
                .roles(Set.of(RoleType.ROLE_USER))
                .build();
        Task task = Task.builder()
                .id(1L)
                .title("Task1")
                .description("Test task")
                .status(Status.WAITING)
                .priority(Priority.MEDIUM)
                .author(user)
                .assignee(user)
                .build();
        Comment comment = Comment.builder()
                .id(1L)
                .comment("Test comment")
                .author(user)
                .createAt(createAt)
                .task(task)
                .build();

        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));

        Comment returnedComment = commentService.findById(1L);

        assertThat(returnedComment.getId()).isEqualTo(comment.getId());
        assertThat(returnedComment.getComment()).isEqualTo(comment.getComment());
        assertThat(returnedComment.getAuthor()).isEqualTo(comment.getAuthor());
        assertThat(returnedComment.getCreateAt()).isEqualTo(comment.getCreateAt());
        assertThat(returnedComment.getTask()).isEqualTo(comment.getTask());

        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound(){

        given(commentRepository.findById(Mockito.any(Long.class))).willReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> {
            Comment returnedComment = commentService.findById(1L);
        });

        assertThat(thrown).isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Comment with id 1 not found");
        verify(commentRepository, times(1)).findById(1L);
    }


    @Test
    void testCreateCommentSuccess() {


        Instant createAt = Instant.now();
        User user = User.builder()
                .id(1L)
                .username("user")
                .email("user@mail.com")
                .password("user")
                .roles(Set.of(RoleType.ROLE_USER))
                .build();
        Task task = Task.builder()
                .id(1L)
                .title("Task1")
                .description("Test task")
                .status(Status.WAITING)
                .priority(Priority.MEDIUM)
                .author(user)
                .assignee(user)
                .build();
        Comment comment = Comment.builder()
                .id(1L)
                .comment("Test comment")
                .author(user)
                .createAt(createAt)
                .task(task)
                .build();
        UpsertCommentRq rq = UpsertCommentRq.builder()
                .comment("Test comment")
                .user_id(1L)
                .task_id(1L)
                .build();


        given(commentRepository.save(comment)).willReturn(comment);

        Comment returnedComment = commentService.create(rq);

        assertThat(returnedComment.getId()).isEqualTo(comment.getId());
        assertThat(returnedComment.getComment()).isEqualTo(comment.getComment());
        assertThat(returnedComment.getAuthor()).isEqualTo(comment.getAuthor());
        assertThat(returnedComment.getCreateAt()).isEqualTo(comment.getCreateAt());
        assertThat(returnedComment.getTask()).isEqualTo(comment.getTask());

        verify(commentRepository, times(1)).save(comment);

    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}