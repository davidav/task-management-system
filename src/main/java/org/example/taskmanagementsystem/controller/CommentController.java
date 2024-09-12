package org.example.taskmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.dto.Result;
import org.example.taskmanagementsystem.dto.StatusCode;
import org.example.taskmanagementsystem.dto.comment.CommentRq;
import org.example.taskmanagementsystem.dto.comment.CommentRqToCommentConverter;
import org.example.taskmanagementsystem.dto.comment.CommentRs;
import org.example.taskmanagementsystem.dto.comment.CommentToCommentRsConverter;
import org.example.taskmanagementsystem.entity.Comment;
import org.example.taskmanagementsystem.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;
    private final CommentToCommentRsConverter commentToCommentRsConverter;
    private final CommentRqToCommentConverter commentRqToCommentConverter;

    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id) {
        CommentRs commentRs = commentToCommentRsConverter.convert(commentService.findById(id));

        return new Result(true, StatusCode.SUCCESS, "Find one success", commentRs);

    }

    @GetMapping
    public Result findAll() {
        List<Comment> comments = commentService.findAll();
        List<CommentRs> commentRs = comments.stream()
                .map(commentToCommentRsConverter::convert)
                .collect(Collectors.toList());

        return new Result(true, StatusCode.SUCCESS, "Find all success", commentRs);
    }

    @PostMapping
    public Result create(@RequestBody @Valid CommentRq rq) {
        Comment commentSaved = commentService.create(
                commentRqToCommentConverter.convert(rq));

        return new Result(true, StatusCode.SUCCESS, "Create success",
                commentToCommentRsConverter.convert(commentSaved));
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Long id, @RequestBody @Valid CommentRq rq) {

        Comment comment = commentService.update(id, commentRqToCommentConverter.convert(rq));

        return new Result(true,StatusCode.SUCCESS,"Update success",
                commentToCommentRsConverter.convert(comment));
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
        return new Result(true,StatusCode.SUCCESS, "Delete success");
    }

}
