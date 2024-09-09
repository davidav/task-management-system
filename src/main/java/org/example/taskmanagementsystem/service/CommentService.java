package org.example.taskmanagementsystem.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.dto.Result;
import org.example.taskmanagementsystem.dto.comment.CommentRs;
import org.example.taskmanagementsystem.dto.comment.UpsertCommentRq;
import org.example.taskmanagementsystem.repo.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@Transactional
@ResponseBody
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Result findByIdRs(Long id) {
        return null;
    }

    public Result create(UpsertCommentRq rq) {
        return null;

    }

    public Result update(Long id, UpsertCommentRq rq) {
        return null;
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);

    }
}
