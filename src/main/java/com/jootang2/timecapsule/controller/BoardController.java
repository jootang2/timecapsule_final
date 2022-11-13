package com.jootang2.timecapsule.controller;

import com.jootang2.timecapsule.domain.Board;
import com.jootang2.timecapsule.domain.Capsule;
import com.jootang2.timecapsule.service.BoardService;
import com.jootang2.timecapsule.service.CapsuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("{capsuleId}/board")
public class BoardController {

    private final CapsuleService capsuleService;
    private final BoardService boardService;

    @GetMapping("/list")
    public String boardMain(@PathVariable Long capsuleId, Model model) {
        Capsule capsule = capsuleService.findById(capsuleId);
        List<Board> boardList = boardService.findAll();
        model.addAttribute("capsule", capsule);
        model.addAttribute("boardList", boardList);
        return "board/boardList";
    }

}
