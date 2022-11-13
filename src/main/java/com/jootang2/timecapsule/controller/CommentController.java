package com.jootang2.timecapsule.controller;

import com.jootang2.timecapsule.domain.Board;
import com.jootang2.timecapsule.domain.Comment;
import com.jootang2.timecapsule.domain.SiteUser;
import com.jootang2.timecapsule.dto.CommentDto;
import com.jootang2.timecapsule.service.BoardService;
import com.jootang2.timecapsule.service.CommentService;
import com.jootang2.timecapsule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
@PreAuthorize("isAuthenticated()")
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;
    private final UserService userService;

    @PostMapping("/create/{boardId}")
    public String createComment(Model model, @PathVariable Long boardId, CommentDto commentDto, Principal principal){
        SiteUser user = userService.findByName(principal.getName());
        Board board = boardService.findById(boardId);
        commentService.create(commentDto, board, user);
        return "redirect:/%d/board/detail/%d".formatted(board.getCapsule().getId(), boardId);
    }

    @GetMapping("/delete/{boardId}/{commentId}")
    public String deleteComment(@PathVariable Long commentId, @PathVariable Long boardId){
        Board board = boardService.findById(boardId);
        Comment comment = commentService.findById(commentId);
        commentService.delete(comment);
        return "redirect:/%d/board/detail/%d".formatted(board.getCapsule().getId(), boardId);
    }

    @PostMapping("/update/{boardId}/{commentId}")
    public String updateComment(@PathVariable Long commentId, @PathVariable Long boardId, CommentDto commentDto){
        Board board = boardService.findById(boardId);
        commentService.update(commentId, commentDto);
        return "redirect:/%d/board/detail/%d".formatted(board.getCapsule().getId(), boardId);
    }
}
