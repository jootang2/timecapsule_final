package com.jootang2.timecapsule.controller;

import com.jootang2.timecapsule.domain.Capsule;
import com.jootang2.timecapsule.domain.SiteUser;
import com.jootang2.timecapsule.dto.UserDto;
import com.jootang2.timecapsule.dto.UserModifyPasswordDto;
import com.jootang2.timecapsule.service.CapsuleService;
import com.jootang2.timecapsule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CapsuleService capsuleService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/join")
    public String join(UserDto userDto) {
        return "user/join";
    }

    @PostMapping("/join")
    public String join(@Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/join";
        }

        if (!userDto.getPassword().equals(userDto.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "user/join";
        }
        try {
            userService.create(userDto);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("joinFailed", "이미 등록된 사용자입니다.");
            return "user/join";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("joinFailed", e.getMessage());
            return "user/join";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/findName")
    public String findName() {
        return "/user/findName";
    }

    @PostMapping("/findName")
    public String findName(@RequestParam String email, Model model) {
        String result = userService.findNameByEmail(email);
        model.addAttribute("result", result);
        return "user/findNameResult";
    }

    @GetMapping("/findPassword")
    public String findPassword() {
        return "user/findPassword";
    }

    @PostMapping("/findPassword")
    public String findPassword(@RequestParam String email, @RequestParam String userName, Model model) {
        String result = userService.resetUserPassword(userName, email);
        model.addAttribute("result", result);
        return "user/findPasswordResult";
    }

    @GetMapping("/myPage")
    @PreAuthorize("isAuthenticated()")
    public String myPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        SiteUser user = userService.findByName(userName);
        List<Capsule> capsuleList = capsuleService.findByUser(user);
        model.addAttribute("capsuleList", capsuleList);
        model.addAttribute("user", user);
        return "user/myPage";
    }

    @GetMapping("/modifyPassword")
    @PreAuthorize("isAuthenticated()")
    public String modifyPassword(UserModifyPasswordDto userModifyPasswordDto) {
        return "user/modifyPassword";
    }

    @PostMapping("/modifyPassword")
    @PreAuthorize("isAuthenticated()")
    public String modifyPassword(@Valid UserModifyPasswordDto userModifyPasswordDto, BindingResult bindingResult) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        SiteUser user = userService.findByName(userName);
        System.out.println("userName = " + userName);
        System.out.println("userModifyPasswordDto = " + userModifyPasswordDto.getOldPassword());
        if (!passwordEncoder.matches(userModifyPasswordDto.getOldPassword(), user.getPassword())) {
            bindingResult.rejectValue("oldPassword", "passwordIncorrect",
                    "원래 비밀번호가 아닙니다.");
            return "user/modifyPassword";
        }
        if (userModifyPasswordDto.getOldPassword().equals(userModifyPasswordDto.getPassword())) {
            bindingResult.rejectValue("password", "samePassword",
                    "원래 비밀번호와 동일한 비밀번호 입니다.");
            return "user/modifyPassword";
        }
        if (!userModifyPasswordDto.getPassword().equals(userModifyPasswordDto.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "passWordInCorrect",
                    "비밀번호가 일치하지 않습니다.");
            return "user/modifyPassword";
        }

        userService.modifyPassword(user, userModifyPasswordDto.getPassword());

        return "redirect:/user/logout";
    }
}
