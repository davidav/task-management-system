package org.example.taskmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.dto.comment.CommentRs;
import org.example.taskmanagementsystem.dto.comment.UpsertCommentRq;
import org.example.taskmanagementsystem.repo.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@ResponseBody
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentRs findByIdRs(Long id) {
        return null;
    }

    public CommentRs create(UpsertCommentRq rq) {
        return null;

    }

    public CommentRs update(Long id, UpsertCommentRq rq) {
        return null;
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);

    }
}
