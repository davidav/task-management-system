package org.example.taskmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.dto.Result;
import org.example.taskmanagementsystem.dto.comment.CommentRs;
import org.example.taskmanagementsystem.dto.comment.UpsertCommentRq;
import org.example.taskmanagementsystem.entity.Comment;
import org.example.taskmanagementsystem.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}")
    public Comment findById(@PathVariable Long id) {
        return commentService.findById(id);
    }

    @PostMapping
    public Comment create(@RequestBody @Valid UpsertCommentRq rq) {
        return commentService.create(rq);
    }

    @PutMapping("/{id}")
    public Comment update(@PathVariable Long id, @RequestBody @Valid UpsertCommentRq rq) {
        return commentService.update(id, rq);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
    }

}
