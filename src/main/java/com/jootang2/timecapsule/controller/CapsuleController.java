package com.jootang2.timecapsule.controller;

import com.jootang2.timecapsule.domain.Capsule;
import com.jootang2.timecapsule.dto.CapsuleDto;
import com.jootang2.timecapsule.service.CapsuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/capsule")
public class CapsuleController {

    private final CapsuleService capsuleService;

    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String CapsuleCreate(CapsuleDto capsuleDto) {
        return "capsule/capsuleCreateForm";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String CapsuleCreate(@Valid CapsuleDto capsuleDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "capsule/capsuleCreateForm";
        }
        capsuleService.create(capsuleDto);
        return "redirect:/capsule/capsuleList/ing";
    }

    @GetMapping("/capsuleList/ing")
    public String CapsuleList(Model model) {
        List<Capsule> capsuleList = capsuleService.findAll();
        model.addAttribute("capsuleList", capsuleList);
        return "capsule/capsuleList";
    }

    @GetMapping("/capsuleList/done")
    public String storageCapsuleList(Model model){
        List<Capsule> capsuleList = capsuleService.findAll();
        model.addAttribute("capsuleList" , capsuleList);
        return "capsule/storageCapsuleList";
    }
}
