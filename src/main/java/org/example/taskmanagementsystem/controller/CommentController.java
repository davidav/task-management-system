package org.example.taskmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.dto.comment.CommentRs;
import org.example.taskmanagementsystem.dto.comment.UpsertCommentRq;
import org.example.taskmanagementsystem.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}")
    public CommentRs findById(@PathVariable Long id) {
        return commentService.findByIdRs(id);
    }

    @PostMapping
    public CommentRs create(@RequestBody @Valid UpsertCommentRq rq) {
        return commentService.create(rq);
    }

    @PutMapping("/{id}")
    public CommentRs update(@PathVariable Long id, @RequestBody @Valid UpsertCommentRq rq) {
        return commentService.update(id, rq);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
    }

}
