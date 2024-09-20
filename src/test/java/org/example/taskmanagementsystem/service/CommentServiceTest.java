package org.example.taskmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;
    @InjectMocks
    CommentService commentService;

    private Comment comment;

    @BeforeEach
    void setUp() {
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
        comment = Comment.builder()
                .id(1L)
                .comment("Test comment")
                .author(user)
                .createAt(createAt)
                .build();
        task.addComment(comment);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {

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
    void testFindByIdNotFailure(){

        given(commentRepository.findById(Mockito.any(Long.class))).willReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> commentService.findById(1L));

        assertThat(thrown).isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Comment with id 1 not found");
        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAllSuccess(){
        Comment c1 = Comment.builder().comment("Comment1").build();
        Comment c2 = Comment.builder().comment("Comment2").build();
        Comment c3 = Comment.builder().comment("Comment3").build();
        List<Comment> comments = List.of(c1, c2, c3);
        given(commentRepository.findAll()).willReturn(comments);

        List<Comment> returnedComments = commentService.findAll();

        assertThat(returnedComments.get(0).getComment()).isEqualTo(comments.get(0).getComment());
        assertThat(returnedComments.get(1).getComment()).isEqualTo(comments.get(1).getComment());
        assertThat(returnedComments.get(2).getComment()).isEqualTo(comments.get(2).getComment());
        verify(commentRepository, times(1)).findAll();

    }

    @Test
    void testCreateCommentSuccess() {

        given(commentRepository.save(comment)).willReturn(comment);

        Comment returnedComment = commentService.create(comment);

        assertThat(returnedComment.getId()).isEqualTo(comment.getId());
        assertThat(returnedComment.getComment()).isEqualTo(comment.getComment());
        assertThat(returnedComment.getAuthor()).isEqualTo(comment.getAuthor());
        assertThat(returnedComment.getCreateAt()).isEqualTo(comment.getCreateAt());
        assertThat(returnedComment.getTask()).isEqualTo(comment.getTask());
        verify(commentRepository, times(1)).save(comment);

    }




    @Test
    void testUpdateCommentSuccess() {

        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));
        given(commentRepository.save(comment)).willReturn(comment);

        Comment returnedComment = commentService.update(1L, comment);

        assertThat(returnedComment.getId()).isEqualTo(comment.getId());
        assertThat(returnedComment.getComment()).isEqualTo(comment.getComment());
        assertThat(returnedComment.getAuthor()).isEqualTo(comment.getAuthor());
        assertThat(returnedComment.getCreateAt()).isEqualTo(comment.getCreateAt());
        assertThat(returnedComment.getTask()).isEqualTo(comment.getTask());
        verify(commentRepository, times(1)).save(comment);


    }

    @Test
    void deleteByIdSuccess() {

        commentService.deleteById(1L);

        verify(commentRepository, times(1)).deleteById(1L);
    }
}