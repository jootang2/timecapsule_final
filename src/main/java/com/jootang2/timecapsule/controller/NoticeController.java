package com.jootang2.timecapsule.controller;

import com.jootang2.timecapsule.domain.Notice;
import com.jootang2.timecapsule.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String list(Model model){
        List<Notice> noticeList = noticeService.findAll();
        model.addAttribute("noticeList", noticeList);
        return "notice/list";
    }

    @GetMapping("/detail/{noticeId}")
    public String detail(@PathVariable Long noticeId, Model model){
        Notice notice = noticeService.findById(noticeId);
        noticeService.addHit(notice);
        model.addAttribute("notice", notice);
        return "notice/detail";
    }
}
