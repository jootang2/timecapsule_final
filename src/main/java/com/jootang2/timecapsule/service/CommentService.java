package com.jootang2.timecapsule.service;

import com.jootang2.timecapsule.domain.Board;
import com.jootang2.timecapsule.domain.Comment;
import com.jootang2.timecapsule.domain.SiteUser;
import com.jootang2.timecapsule.dto.CommentDto;
import com.jootang2.timecapsule.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void create(CommentDto commentDto, Board board, SiteUser user) {
        Comment comment = new Comment();
        comment.setBoard(board);
        comment.setContent(commentDto.getContent());
        comment.setUser(user);
        commentRepository.save(comment);
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(null);
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    public void update(Long commentId, CommentDto commentDto) {
        Comment comment = findById(commentId);
        comment.setContent(commentDto.getContent());
        commentRepository.save(comment);
    }
}
