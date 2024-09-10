package org.example.taskmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.dto.Result;
import org.example.taskmanagementsystem.dto.comment.CommentRs;
import org.example.taskmanagementsystem.dto.comment.UpsertCommentRq;
import org.example.taskmanagementsystem.entity.Comment;
import org.example.taskmanagementsystem.repo.CommentRepository;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;

@Service
@Transactional
@ResponseBody
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        MessageFormatter.format("Comment with id {} not found", id).getMessage()));
    }

    public Comment create(UpsertCommentRq rq) {

//        commentRepository.save(rq);
        return null;

    }

    public Comment update(Long id, UpsertCommentRq rq) {
        return null;
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);

    }
}
