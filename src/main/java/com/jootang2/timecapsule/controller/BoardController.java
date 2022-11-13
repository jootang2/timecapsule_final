package com.jootang2.timecapsule.controller;

import com.jootang2.timecapsule.domain.Board;
import com.jootang2.timecapsule.domain.Capsule;
import com.jootang2.timecapsule.domain.SiteUser;
import com.jootang2.timecapsule.dto.BoardDto;
import com.jootang2.timecapsule.service.BoardService;
import com.jootang2.timecapsule.service.CapsuleService;
import com.jootang2.timecapsule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("{capsuleId}/board")
public class BoardController {

    private final CapsuleService capsuleService;
    private final BoardService boardService;
    private final UserService userService;
    @GetMapping("/list")
    public String boardMain(@PathVariable Long capsuleId, Model model) {
        Capsule capsule = capsuleService.findById(capsuleId);
        List<Board> boardList = boardService.findAll();
        model.addAttribute("capsule", capsule);
        model.addAttribute("boardList", boardList);
        return "board/boardList";
    }

    @GetMapping("/create")
    public String boardCreate(BoardDto boardDto, @PathVariable Long capsuleId, Model model) {
        Capsule capsule = capsuleService.findById(capsuleId);
        model.addAttribute("capsule", capsule);

        return "board/boardWriteForm";

    }

    @PostMapping("/create")
    public String boardCreate(BoardDto boardDto, Model model, @PathVariable Long capsuleId, Capsule capsule) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        SiteUser user = userService.findByName(userName);
        capsule = capsuleService.findById(capsuleId);
        boardService.create(boardDto, capsule, user);
        return "redirect:/%d/board/list".formatted(capsuleId);
    }

}
