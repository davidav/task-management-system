package org.example.taskmanagementsystem.controller;

import org.example.taskmanagementsystem.entity.Comment;
import org.example.taskmanagementsystem.service.CommentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommentService commentService;

    @InjectMocks
    CommentController commentController;

    List<Comment> comments;

    @BeforeEach
    void setUp() {
        Comment c1 = Comment.builder().comment("Comment1").build();
        Comment c2 = Comment.builder().comment("Comment2").build();
        Comment c3 = Comment.builder().comment("Comment3").build();
        comments = List.of(c1, c2, c3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {

        given(commentService.findById(1L)).willReturn(comments.get(0));

        commentController.findById(1L);




    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}