package com.jootang2.timecapsule.controller;

import com.jootang2.timecapsule.domain.Board;
import com.jootang2.timecapsule.domain.Capsule;
import com.jootang2.timecapsule.domain.SiteUser;
import com.jootang2.timecapsule.dto.BoardDto;
import com.jootang2.timecapsule.dto.CapsuleDto;
import com.jootang2.timecapsule.service.BoardService;
import com.jootang2.timecapsule.service.CapsuleService;
import com.jootang2.timecapsule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("{capsuleId}/board")
@PreAuthorize("isAuthenticated()")
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

    @GetMapping("/detail/{boardId}")
    public String boardDetail(@PathVariable Long boardId, @PathVariable Long capsuleId ,Model model, Principal principal) {
        Board board = boardService.findById(boardId);
        Capsule capsule = capsuleService.findById(capsuleId);
        SiteUser user = userService.findByName(principal.getName());
        boardService.addHit(board);
        model.addAttribute("user", user);
        model.addAttribute("board", board);
        model.addAttribute("capsule", capsule);
        return "board/boardDetail";
    }

    @GetMapping("/password/{accessKey}")
    public String boardPassword(CapsuleDto capsuleDto , @PathVariable Long capsuleId, @PathVariable String accessKey, Model model) {
        Capsule capsule = capsuleService.findById(capsuleId);
        if(accessKey.equals("defaultKey") && !capsule.getCapsuleAccessKey().equals("defaultKey")){
            return "deny";
        }
        if(accessKey.equals("defaultKey")){
            model.addAttribute("capsule", capsule);

            return "board/boardPassword";
        } else if(accessKey.equals(capsule.getCapsuleAccessKey())){
            //?????? ????????? ?????? ??? ??????????????? ???????????? ??????
            model.addAttribute("capsule", capsule);

            return "capsule/takeOut";
        } else {
            return "deny";
        }

    }

    @PostMapping("/password")
    public String boardPassword(@Valid CapsuleDto capsuleDto, BindingResult bindingResult, @PathVariable Long capsuleId, Model model, HttpServletRequest request) {
        Capsule capsule = capsuleService.findById(capsuleId);
        List<Board> boardList = boardService.findAll();
        HttpSession session = request.getSession();
        //?????? ????????? ??????
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        session.setAttribute("capsule" + capsuleId + "access", capsuleDto.getCapsulePassword());
        //????????? ????????? ?????? ??????????????? ??????
        model.addAttribute("capsule", capsule);
        model.addAttribute("boardList", boardList);

        if (!encoder.matches(session.getAttribute("capsule" + capsuleId + "access").toString(),capsule.getCapsulePassWord())) {
            session.removeAttribute("capsule" + capsuleId + "access");
            // ??????????????? ????????????????????? ???????????? ??? ??????
            bindingResult.rejectValue("capsulePassword", "passwordInCorrect",
                    "??????????????? ???????????? ????????????.");
            return "board/boardPassword";
            // ??????????????? ???????????? ????????? ?????? ????????? ????????? ????????????
        }
        return "redirect:/%d/board/list".formatted(capsuleId);
    }

    @GetMapping("/update/{boardId}")
    public String boardUpdate(@PathVariable Long boardId, @PathVariable Long capsuleId ,Model model, BoardDto boardDto) {
        Capsule capsule = capsuleService.findById(capsuleId);
        Board board = boardService.findById(boardId);
        model.addAttribute("capsule", capsule);
        model.addAttribute("board", board);

        return "board/boardUpdateForm";
    }

    @PostMapping("/update/{boardId}")
    public String boardUpdate(@PathVariable Long boardId,@PathVariable Long capsuleId, BoardDto boardDto) {
        boardService.update(boardId, boardDto);
        return "redirect:/%d/board/detail/%d".formatted(capsuleId, boardId);
    }

    @GetMapping("/delete/{boardId}")
    public String boardDelete(@PathVariable Long boardId, @PathVariable Long capsuleId) {
        boardService.delete(boardId);
        return "redirect:/%d/board/list".formatted(capsuleId);
    }

    @GetMapping("/storage")
    public String storage(@PathVariable Long capsuleId, Model model) {
        Capsule capsule = capsuleService.findById(capsuleId);
        model.addAttribute("capsule", capsule);
        return "storage";
    }

    @PostMapping("/storage")
    public String storage(@PathVariable Long capsuleId, CapsuleDto capsuleDto) {
        Capsule capsule = capsuleService.findById(capsuleId);
        capsuleService.storageCapsule(capsuleId, capsuleDto);
        return "index";
    }

}
