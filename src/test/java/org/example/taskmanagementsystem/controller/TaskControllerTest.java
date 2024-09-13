package org.example.taskmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.taskmanagementsystem.dto.StatusCode;
import org.example.taskmanagementsystem.dto.comment.CommentRqToCommentConverter;
import org.example.taskmanagementsystem.dto.task.TaskRqToTaskConvertor;
import org.example.taskmanagementsystem.dto.task.TaskToTaskRsConvertor;
import org.example.taskmanagementsystem.entity.*;
import org.example.taskmanagementsystem.service.CommentService;
import org.example.taskmanagementsystem.service.TaskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TaskToTaskRsConvertor taskToTaskRsConvertor;

    @MockBean
    TaskRqToTaskConvertor taskRqToTaskConvertor;

    @MockBean
    TaskService taskService;

    private List<Task> tasks;

    @BeforeEach
    void setUp() {
        Instant createAt = Instant.now();
        User admin = User.builder()
                .id(1L)
                .username("admin")
                .password("admin")
                .email("admin@mail.com")
                .roles(Set.of(RoleType.ROLE_ADMIN))
                .build();
        User user = User.builder()
                .id(2L)
                .username("user")
                .password("user")
                .email("user@mail.com")
                .roles(Set.of(RoleType.ROLE_USER))
                .build();
        Task t1 = Task.builder()
                .id(1L)
                .title("Task1")
                .description("Description task 1")
                .status(Status.WAITING)
                .priority(Priority.LOW)
                .author(admin)
                .assignee(user)
                .createdAt(createAt)
                .comments(new ArrayList<>())
                .build();
        Task t2 = Task.builder()
                .id(2L)
                .title("Task2")
                .description("Description task 2")
                .status(Status.WAITING)
                .priority(Priority.HIGH)
                .author(user)
                .assignee(admin)
                .createdAt(createAt)
                .comments(new ArrayList<>())
                .build();
        Comment c1 = Comment.builder().id(1L).comment("Comment1").author(admin).createAt(createAt).build();
        Comment c2 = Comment.builder().id(2L).comment("Comment2").author(user).createAt(createAt).build();
        Comment c3 = Comment.builder().id(3L).comment("Comment3").author(admin).createAt(createAt).build();
        t1.addComment(c1);
        t1.addComment(c2);
        t2.addComment(c3);
        tasks = List.of(t1, t2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() throws Exception {

        given(taskService.findById(1L)).willReturn(tasks.get(0));

        this.mockMvc.perform(get("/api/v1/task/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find one success"))
                .andExpect(jsonPath("$.data.title").value("Task1"));
    }



}