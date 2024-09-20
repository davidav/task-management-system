package org.example.taskmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.taskmanagementsystem.entity.*;
import org.example.taskmanagementsystem.repo.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskService taskService;

    private Task task;
    private final Instant createAt = Instant.now();


    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id(1L)
                .username("user")
                .email("user@mail.com")
                .password("user")
                .roles(Set.of(RoleType.ROLE_USER))
                .build();
        task = Task.builder()
                .id(1L)
                .title("Task")
                .description("Description task")
                .status(Status.WAITING)
                .priority(Priority.MEDIUM)
                .author(user)
                .assignee(user)
                .createdAt(createAt)
                .build();
        Comment comment1 = Comment.builder()
                .id(1L)
                .comment("Test comment 1")
                .author(user)
                .createAt(createAt)
                .task(task)
                .build();
        Comment comment2 = Comment.builder()
                .id(2L)
                .comment("Test comment 2")
                .author(user)
                .createAt(createAt)
                .task(task)
                .build();
        task.addComment(comment1);
        task.addComment(comment2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByIdTaskWithCommentSuccess() {

        given(taskRepository.findById(1L)).willReturn(Optional.of(task));

        Task returnedTask = taskService.findById(1L);

        assertThat(returnedTask.getId()).isEqualTo(1L);
        assertThat(returnedTask.getTitle()).isEqualTo("Task");
        assertThat(returnedTask.getDescription()).isEqualTo("Description task");
        assertThat(returnedTask.getAuthor().getId()).isEqualTo(1L);
        assertThat(returnedTask.getAssignee().getId()).isEqualTo(1L);
        assertThat(returnedTask.getComments().get(0).getComment()).isEqualTo("Test comment 1");
        assertThat(returnedTask.getComments().get(1).getComment()).isEqualTo("Test comment 2");
        assertThat(returnedTask.getCreatedAt()).isEqualTo(createAt);
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdTaskWithCommentFailure() {
        given(taskRepository.findById(1L)).willReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> taskService.findById(1L));

        assertThat(thrown).isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Task with id 1 not found");
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAllTasksSuccess(){

        Task task1 = Task.builder().id(1L).title("Task1").build();
        List<Task> tasks = List.of(task, task1);

        given(taskRepository.findAll()).willReturn(tasks);

        List<Task> returnedTasks = taskService.findAll();

        assertThat(returnedTasks.get(0).getTitle()).isEqualTo("Task");
        assertThat(returnedTasks.get(1).getTitle()).isEqualTo("Task1");
        verify(taskRepository, times(1)).findAll();

    }

    @Test
    void testCreateTaskSuccess() {

        given(taskRepository.save(task)).willReturn(task);

        Task returnedTask = taskService.create(task);

        assertThat(returnedTask.getId()).isEqualTo(1L);
        assertThat(returnedTask.getTitle()).isEqualTo("Task");
        assertThat(returnedTask.getDescription()).isEqualTo("Description task");
        assertThat(returnedTask.getCreatedAt()).isEqualTo(createAt);
        verify(taskRepository, times(1)).save(task);

    }

    @Test
    void testUpdateTaskSuccess() {
        Task updateTask = Task.builder()
                .id(1L)
                .title("TaskUp")
                .description("Update task")
                .createdAt(createAt).build();
        given(taskRepository.save(task)).willReturn(updateTask);
        given(taskRepository.findById(1L)).willReturn(Optional.of(task));

        Task returnedTask = taskService.update(1L, updateTask);

        assertThat(returnedTask.getId()).isEqualTo(1L);
        assertThat(returnedTask.getTitle()).isEqualTo("TaskUp");
        assertThat(returnedTask.getDescription()).isEqualTo("Update task");
        assertThat(returnedTask.getCreatedAt()).isEqualTo(createAt);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void deleteTaskByIdSuccess() {
        taskService.deleteById(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }


}