package com.jootang2.timecapsule.controller;

import com.jootang2.timecapsule.domain.Capsule;
import com.jootang2.timecapsule.dto.CapsuleDto;
import com.jootang2.timecapsule.service.BoardService;
import com.jootang2.timecapsule.service.CapsuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/capsule")
public class CapsuleController {

    private final CapsuleService capsuleService;
    private final BoardService boardService;

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
        List<Capsule> writingCapsuleList = new ArrayList<>();
        for(Capsule capsule : capsuleList){
            if(capsule.getCapsuleStatus().equals("writing")){
                writingCapsuleList.add(capsule);
            }
        }
        model.addAttribute("writingCapsuleList", writingCapsuleList);
        return "capsule/capsuleList";
    }

    @GetMapping("/capsuleList/done")
    public String storageCapsuleList(Model model){
        List<Capsule> capsuleList = capsuleService.findAll();
        List<Capsule> storageCapsuleList = new ArrayList<>();
        for(Capsule capsule : capsuleList){
            if(capsule.getCapsuleStatus().equals("storage")){
                storageCapsuleList.add(capsule);
            }
        }
        model.addAttribute("storageCapsuleList", storageCapsuleList);
        return "capsule/storageCapsuleList";
    }

    @PostMapping("/delete/{capsuleId}")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable Long capsuleId){
        boardService.deleteByCapsule(capsuleId);
        capsuleService.delete(capsuleId);
        return "redirect:/capsule/capsuleList/ing";
    }
}
