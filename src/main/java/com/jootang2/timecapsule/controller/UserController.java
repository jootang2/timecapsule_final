package com.jootang2.timecapsule.controller;

import com.jootang2.timecapsule.dto.UserDto;
import com.jootang2.timecapsule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

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
}
